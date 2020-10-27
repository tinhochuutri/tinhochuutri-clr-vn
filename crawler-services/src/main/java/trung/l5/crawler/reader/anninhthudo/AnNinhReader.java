package trung.l5.crawler.reader.anninhthudo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.model.TypeCategory;
import trung.l5.crawler.reader.IReader;
import trung.l5.crawler.schedules.anninhthudo.AnNinhCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class AnNinhReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(AnNinhCrawlerItemListJob.class);

    public AnNinhReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("https://anninhthudo.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/chinh-tri-xa-hoi/3.antd?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/phap-luat/80.antd?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/the-gioi/7.antd?page=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://anninhthudo.vn/Chuyen-dong-van-hoa/.antd?page=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/kinh-doanh/6.antd?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/the-thao/9.antd?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/giai-tri/8.antd?page=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://anninhthudo.vn/giao-duc/.antd?page=" + i;
//            pages.add(currentPage);
//        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://anninhthudo.vn/y-te/.antd?page=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://anninhthudo.vn/oto-xe-may/136.antd?page=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://anninhthudo.vn/o/.antd?page=" + i;
//            pages.add(currentPage);
//        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://anninhthudo.vn/gia-dinh-hi-tech/.antd?page=" + i;
//            pages.add(currentPage);
//        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://anninhthudo.vn") {
            Elements bigNews = doc.select(".focus-wrap > article.focus");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://anninhthudo.vn" + bigNews.select("a").first().attr("href");
                String title = bigNews.select("a").first().attr("title");
                String thumb = bigNews.select("a > img").attr("src");
//                String contentText = bigNews.select("p").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
//                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItemHoriztal = doc.select(".feature > article");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://anninhthudo.vn" + e.select("a").first().attr("href");
                    String title = e.select("a.cms-link").first().text();

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

//            .spec-news-sitebar >.trending.first
            Elements newsBreakingItemVetical = doc.select(".timeline > article");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://anninhthudo.vn" + r.select("a").first().attr("href");
                    String title = r.select("a").first().attr("title");
                    String thumb = r.select("a > img").attr("src");
                    String contentText = r.select(".summary").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    appModel.setContentText(contentText);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".focus-wrap > article");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://anninhthudo.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select("a > img").attr("src");
                String contentText = contentBreaking.select(".summary").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

//            Elements contentListTop = doc.select(".feature > article");
//            try {
//                Thread.sleep(1000);
//                String sourceUrl = "https://anninhthudo.vn" +  contentBreaking.select("a").first().attr("href");
//                String title =  contentBreaking.select("a").first().attr("title");
//                String thumb = contentBreaking.select("a > img").attr("src");
//                AppModel appModel = new AppModel();
//                appModel.setSourceLink(sourceUrl);
//                appModel.setTitle(title);
//                appModel.setThumb(thumb);
//                setItem(result, appModel, catNews, pageUrl, sourceUrl);
//            } catch (Exception e2) {
//                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
//            }
            Elements contentBreakingRight = doc.select(".feature > article");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://anninhthudo.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }
            Elements contentBreakingBottom = doc.select(".timeline > article");
            for (Element e : contentBreakingBottom) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://anninhthudo.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    String contentText = e.select(".summary").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    appModel.setContentText(contentText);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception e2) {
                    // ghi log
                }
            }
//            Elements contentItemsEle = doc.select(".listtotal1 > .showlist > ul > li");
//            for (Element e : contentItemsEle) {
//                try {
//                    Thread.sleep(1000);
//                    String sourceUrl = "https://anninhthudo.vn" +  e.select("a").first().attr("href");
//                    String title =  e.select("a").first().attr("title");
//                    String thumb = e.select("a > img").attr("src");
//                    String contentText = e.select("p").first().text();
//                    AppModel appModel = new AppModel();
//                    appModel.setSourceLink(sourceUrl);
//                    appModel.setTitle(title);
//                    appModel.setThumb(thumb);
//                    appModel.setContentText(contentText);
//                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
//
//                } catch (Exception e2) {
//                    // ghi log
//                }
//            }
        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: ANTD");
        if (pageUrl == "https://anninhthudo.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://anninhthudo.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://anninhthudo.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://anninhthudo.vn/the-thao/")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://anninhthudo.vn/chinh-tri-xa-hoi")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://anninhthudo.vn/kinh-doanh/")) {
            appModel.setCatid(catNews.kinh_te);
        }
//        if (pageUrl.contains("https://anninhthudo.vn/gia-dinh-hi-tech/")) {
//            appModel.setCatid(catNews.cong_nghe);
//        }

//        if (pageUrl.contains("https://anninhthudo.vn/Chuyen-dong-van-hoa/")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("https://anninhthudo.vn/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
//        if (pageUrl.contains("https://anninhthudo.vn/giao-duc/")) {
//            appModel.setCatid(catNews.giao_duc);
//        }
//        if (pageUrl.contains("https://anninhthudo.vn/y-te/")) {
//            appModel.setCatid(catNews.suc_khoe);
//        }
//        if (pageUrl.contains("https://anninhthudo.vn/o/")) {
//            appModel.setCatid(catNews.nha_dat);
//        }
        if (pageUrl.contains("https://anninhthudo.vn/oto-xe-may")) {
            appModel.setCatid(catNews.xe_co);
        }

        appModel.setId(appModel.convertMd5ToInt(HashUtils.md5(sourceUrl)) > 0
                ? appModel.convertMd5ToInt(HashUtils.md5(sourceUrl))
                : Math.abs(appModel.convertMd5ToInt(HashUtils.md5(sourceUrl))));
        readItem(appModel, sourceUrl);
        result.add(appModel);
    }

    @Override
    public void readItem(AppModel appModel, String itemUrl) throws Exception {
        Document doc = Jsoup.connect(itemUrl).get();

        try {
            if (appModel.getContentText() == null || appModel.getContentText() == "") {
                try {
                    String contentT = doc.select(".summary.cms-desc").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {
                }

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
//            Elements authorRaw = doc.select(".aliaspost");
//            if (authorRaw.isEmpty()) {
//                authorRaw = doc.select(".detail-auth");
//            }
//            String author = "<p><strong>" + authorRaw.text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select(".cms-date").attr("content");
//                    if (datePost.isEmpty()) {
//                        datePost = doc.select(".detail-timer").text();
//                    }
                } catch (Exception e) {

                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT"));
                Pattern p = Pattern.compile("([0-9]{4})-([0-9]{2})-([0-9]{2})T([0-9]{2}:[0-9]{2})");
                Matcher m = p.matcher(datePost);
                if (m.find()) {
                    try {
                        String date = m.group(3) + "/" + m.group(2) + "/" + m.group(1) + " " + m.group(4);
                        Date createdDate = dt.parse(date);
//                        dt.format(createdDate);
                        appModel.setCreatedDate(createdDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            }
            String contentRaw = "";
            Boolean isGallery = false;
            try {
                contentRaw = doc.select("#main_detail").toString();
                if (contentRaw.isEmpty()) {
                    contentRaw = doc.select("#gallery").toString();
                    isGallery = true;
                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

//            String bottomNews = doc.select("h4.subbottomnew").toString().replaceAll("(\\s{2,1000}|\\n)", "");
//            String releasted = doc.select(".VCSortableInPreviewMode[type=RelatedOneNews]").toString().replaceAll("(\\s{2,1000}|\\n)", "");;
//            contentRaw = contentRaw.replaceAll(bottomNews, "");
//            contentRaw = contentRaw.replaceAll(releasted, "");

//            try {
//                Elements qc = doc.select(".box-widget.post-content > ul.contref");
//                if (qc.isEmpty()) {
//                    qc = doc.select(".detail-content > ul.contref");
//                }
//                for (Element e : qc) {
//                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
//                    contentRaw = contentRaw.replace(el, "");
//                }
//            } catch (Exception e) {
//            }
//            try {
//
//                Elements qc = doc.select(".cms-body >* .position-code");
//                for (Element e : qc) {
//                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
//                    contentRaw = contentRaw.replace(el, "");
//                }
//            } catch (Exception e) {
//            }
            if (!contentRaw.isEmpty()) {
                String imgThumb = "";
                try {
                    if (isGallery) {
                        imgThumb = doc.select("#gallery > * img").first().attr("src");
                    } else {
                        imgThumb = doc.select("#main_detail > * img").first().attr("src");
                    }

                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
//                    try {
//                        if (imgThumb.isEmpty()) {
//                            imgThumb = doc.select(".detail-content >* .img > img").first().attr("src");
//                        }
//                    } catch (Exception er) {
//                    }
                }

//                try {
//                    Elements relatePost = doc.select("article.cms-relate");
//                    for (Element e : relatePost) {
//                        String re = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
//                        contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
//                        contentRaw = contentRaw.replace(re, "");
//                    }
//
//                } catch (Exception e) {
//                }

//                try {
//                    String coTheThich = doc.select(".relatedHead").toString();
//                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
//                    contentRaw = contentRaw.replaceAll(coTheThich, "");
//                } catch (Exception e) {
//                }
//                try {
//                    String ulThich = doc.select(".mainRelated").toString();
//                    ulThich = ulThich.replaceAll("(\\s{2,1000}|\\n)", "");
//                    contentRaw = contentRaw.replaceAll(ulThich, "");
//                } catch (Exception e) {
//                }
                Elements hinhAnh = null;
                if (isGallery) {
                    hinhAnh = doc.select("#gallery > .picitem");
                } else  {
                    hinhAnh = doc.select("#main_detail > * img.cms-photo");
                }
//                if (hinhAnh.isEmpty()) {
//                    hinhAnh = doc.select("div > img");
////                    if (hinhAnh.isEmpty()) {
////                        hinhAnh = doc.select(".detail-content > * table.contentimg");
////                    }
//                }
//                if (!itemUrl.contains("infographic")) {
//                    hinhAnh = doc.select(".cms-body > table.contentimg");
//                } else {
//                    hinhAnh = doc.select(".info-content > table.contentimg");
//                }
                for (Element el : hinhAnh) {
                   if(isGallery) {
                       String LinkAnh = "<p><img src=\"" + el.select("img").attr("src") + "\" /></p>";
                       String motaAnh = "<p><em>" + el.select(".title > em").text() + "</em></p>";
                       String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                       contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                   } else {
                       String LinkAnh = "<p><img src=\"" + el.select("img").attr("src") + "\" /></p>";
                       String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                       contentRaw = contentRaw.replace(elHinhAnh, LinkAnh);
                   }
                }
            }
            try {
                if(!isGallery) {
                    Elements iframeVideos = doc.select("#main_detail > .cms-video");
                    Pattern p = Pattern.compile("videoSrc\\s=(\\s|\\n)\"(https.*?.(mp4|mov|avi))\"");
                    Matcher m = p.matcher(iframeVideos.toString());
                    if (m.find()) {
                        try {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo(m.group(2));
                            String eAtferValidate = iframeVideos.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(2) + "\"> </video>");
                        } catch (Exception err) {
                        }
                    }
                }

            } catch (Exception e) {
            }


            contentRaw = contentRaw.replaceAll("(<span.*?cms-author.*?>)", "<strong>");
            contentRaw = contentRaw.replaceAll("<span class=\"fig\">.*?<\\/span>", "");
            contentRaw = contentRaw.replaceAll("(<b\\s?style.*?>|<b>)", "<strong>");
            contentRaw = contentRaw.replaceAll("<\\/b>", "</strong>");
            contentRaw = contentRaw.replaceAll("&nbsp;", " ");
            contentRaw = contentRaw.replaceAll("(<div*[^\\>]+>|<address>)", "<p>");
            contentRaw = contentRaw.replaceAll("(</div>|</address>)", "</p>");
            contentRaw = contentRaw.replaceAll("<p[^\\>]+>", "<p>");
            contentRaw = contentRaw.replaceAll("<span*[^\\>]+>", "");
            contentRaw = contentRaw.replaceAll("</span>", "");
            contentRaw = contentRaw.replaceAll("(<p>(\\s?)<\\/p>)", "");
            contentRaw = contentRaw.replaceAll("&amp;", "&");
            contentRaw = contentRaw.replaceAll("(<p><iframe.*?<\\/p>|<script.*?<\\/script>|<br>|<blockquote.*?>|<\\/blockquote>|<hr>)", "");
            contentRaw = contentRaw.replaceAll("(<i>|(<em\\sstyle.*?>))", "<em>");
            contentRaw = contentRaw.replaceAll("<\\/i>", "</em>");
            contentRaw = contentRaw.replaceAll("(<center><\\/center>|(<ins.*?<\\/ins>)|(<!--.*?-->)|(<center>)|(<\\/center>)|(<table.*?<td.*?>)|(<\\/td?>.*<\\/table>))", "");
            contentRaw = contentRaw.replace("<p></p>", "");
            appModel.setContentRaw(contentRaw);
            String contentHtml = "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n" +
                    "</head>\n" +

                    content + "\n" + source + "\n" + contentText
                    + "\n<div class='content-detail'>\n" +
                    "  <div>\n" + contentRaw + "\n" + " </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "</html>";
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
