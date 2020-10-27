package trung.l5.crawler.reader.cand;

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
import trung.l5.crawler.schedules.cand.CandCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class CandReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(CandCrawlerItemListJob.class);

    public CandReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("http://cand.com.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/doi-song/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/phap-luat/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/quoc-te/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/Chuyen-dong-van-hoa/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/kinh-te/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/the-thao/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/Giai-tri-van-hoa/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/giao-duc/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/y-te/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/the-gioi-phuong-tien/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/dia-oc/page-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://cand.com.vn/cong-nghe/page-" + i + "/";
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "http://cand.com.vn") {
            Elements bigNews = doc.select(".spec-news > .feature > ul > li");
            try {
                Thread.sleep(1000);
                String sourceUrl = bigNews.select(".imgnews > a").first().attr("href");
                String title = bigNews.select(".featurebody > h1").text();
                String thumb = bigNews.select(".imgnews > a > img").attr("src");
                String contentText = bigNews.select(".featurebody > .summary").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItemHoriztal = doc.select(".feature3 > .article");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select(".imgnews > a").first().attr("href");
                    String title = e.select("h1").text();
                    String thumb = e.select(".imgnews > a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

//            .spec-news-sitebar >.trending.first
            Elements newsBreakingItemVetical = doc.select(".spec-news-sitebar >.article.trending");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = r.select("h1 > a").first().attr("href");
                    String title = r.select("h1").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".feature");
            try {
                Thread.sleep(1000);
                String sourceUrl = contentBreaking.select(".imgnews > a").first().attr("href");
                String title = contentBreaking.select(".imgnews > a").first().attr("title");
                String thumb = contentBreaking.select(".imgnews > a > img").attr("src");
                String contentText = contentBreaking.select(".summary").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentBreakingRight = doc.select(".feature3 > .article");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select(".imgnews > a").first().attr("href");
                    String title = e.select(".imgnews > a").first().attr("title");
                    String thumb = e.select(".imgnews > a > img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }

            Elements contentItemsEle = doc.select(".listnews > .article");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("h1 > a").first().attr("href");
                    String title = e.select("h1 > a").first().attr("title");
                    String thumb = e.select(".imgnews > a > img").attr("src");
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
        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: CAND");
        if (pageUrl == "http://cand.com.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("http://cand.com.vn/phap-luat/")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("http://cand.com.vn/quoc-te/")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("http://cand.com.vn/the-thao/")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("http://cand.com.vn/doi-song/")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("http://cand.com.vn/kinh-te/")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("http://cand.com.vn/cong-nghe/")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("http://cand.com.vn/Chuyen-dong-van-hoa/")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("http://cand.com.vn/Giai-tri-van-hoa/")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("http://cand.com.vn/giao-duc/")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("http://cand.com.vn/y-te/")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("http://cand.com.vn/dia-oc/")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("http://cand.com.vn/the-gioi-phuong-tien/")) {
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
//        itemUrl = "http://antg.cand.com.vn/Kinh-te-Van-hoa-The-Thao/Cuoc-chay-dua-bao-ho-cong-nghe-Ai-huong-loi-545883/";

        Document doc = Jsoup.connect(itemUrl).get();

        try {
            if (appModel.getContentText() == null || appModel.getContentText() == "") {
                try {
                    String contentT = doc.select(".box-widget.desnews").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {
                }

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            Elements authorRaw = doc.select(".aliaspost");
            if (authorRaw.isEmpty()) {
                authorRaw = doc.select(".detail-auth");
            }
            String author = "<p><strong>" + authorRaw.text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select(".box-widget.timepost").text();
                    if(datePost.isEmpty()) {
                        datePost = doc.select(".detail-timer").text();
                    }
                } catch (Exception e) {

                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT"));
                Pattern p = Pattern.compile("([0-9]{2}:[0-9]{2})(\\s*)([0-9]*\\/[0-9]*\\/\\d{4})");
                Matcher m = p.matcher(datePost);
                if (m.find()) {
                    try {
                        Date createdDate = dt.parse(m.group(3) + " " + m.group(1));
//                        dt.format(createdDate);
                        appModel.setCreatedDate(createdDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            }
            String contentRaw = "";
            try {
                contentRaw = doc.select(".box-widget.post-content").toString();
                if (contentRaw.isEmpty()) {
                    contentRaw = doc.select(".detail-content").toString();
                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
            String authorRawString = authorRaw.toString().replaceAll("(\\s{2,1000}|\\n)", "");
            contentRaw = contentRaw.replaceAll(authorRawString, "");

            try {
                Elements qc = doc.select(".box-widget.post-content > ul.contref");
                if (qc.isEmpty()) {
                    qc = doc.select(".detail-content > ul.contref");
                }
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
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
                    imgThumb = doc.select(".box-widget.post-content >* .img > img").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                    try {
                        if (imgThumb.isEmpty()) {
                            imgThumb = doc.select(".detail-content >* .img > img").first().attr("src");
                            if (!imgThumb.isEmpty()) {
                                appModel.setThumb(imgThumb);
                            }
                        }
                    } catch (Exception er) {
                    }
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
                Elements hinhAnh = doc.select(".box-widget.post-content >.contentimg");
                if (hinhAnh.isEmpty()) {
                    hinhAnh = doc.select(".box-widget.post-content > * table.contentimg");
                    if (hinhAnh.isEmpty()) {
                        hinhAnh = doc.select(".detail-content > * table.contentimg");
                    }
                }
//                if (!itemUrl.contains("infographic")) {
//                    hinhAnh = doc.select(".cms-body > table.contentimg");
//                } else {
//                    hinhAnh = doc.select(".info-content > table.contentimg");
//                }
                for (Element el : hinhAnh) {
                    String LinkAnh = "<p><img src=\"" + el.select("tbody > tr > td.img > img").attr("src") + "\" /></p>";
                    String motaAnh = "<p><em>" + el.select("tbody > tr > td.note").text() + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                }
            }
//            try {
//                Elements iframeVideos = doc.select(".cms-body >* script");
//                for (Element e : iframeVideos) {
//                    Pattern p = Pattern.compile("((https:\\/\\/streaming.vov.vn.*?.(mp4|mov))',)");
//                    Matcher m = p.matcher(e.toString());
//                    if (m.find()) {
//                        try {
//                            appModel.setIsVideo(true);
//                            appModel.setLinkVideo(m.group(2));
//                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
//                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(2) + "\"> </video>");
//                        } catch (Exception err) {
//                        }
//                    }
//                }
//            } catch (Exception e) {
//            }


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
            contentRaw = contentRaw.replaceAll("<i>", "<em>");
            contentRaw = contentRaw.replaceAll("<\\/i>", "</em>");
            contentRaw = contentRaw.replaceAll("(<center><\\/center>|(<ins.*?<\\/ins>)|(<!--.*?-->)|(<center>)|(<\\/center>))", "");
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
                    "  <div>\n" + contentRaw + author + "\n" + " </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "</html>";
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
