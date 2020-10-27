package trung.l5.crawler.reader.giadinhnetvn;

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
import trung.l5.crawler.schedules.giadinhnetvn.GiaDinhNetVnCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class GiaDinhNetVnReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(GiaDinhNetVnCrawlerItemListJob.class);

    public GiaDinhNetVnReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("http://giadinh.net.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/xa-hoi/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/phap-luat/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/bon-phuong/trang-" + i + ".htm";
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "http://giadinh.net.vn/Chuyen-dong-van-hoa/trang-" + i + ".htm";
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/thi-truong/trang-" + i + ".htm";
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "http://giadinh.net.vn/the-thao/trang-" + i + ".htm";
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/giai-tri/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/giao-duc/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/y-te/trang-" + i + ".htm";
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "http://giadinh.net.vn/the-gioi-phuong-tien/trang-" + i + ".htm";
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/o/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://giadinh.net.vn/gia-dinh-hi-tech/trang-" + i + ".htm";
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "http://giadinh.net.vn") {
            Elements bigNews = doc.select(".homehlr > .hltop");
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://giadinh.net.vn" +  bigNews.select("a").first().attr("href");
                String title =  bigNews.select("a").first().attr("title");
                String thumb = bigNews.select("a > img").attr("src");
                String contentText = bigNews.select("p").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItemHoriztal = doc.select(".hlother > ul > li");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://giadinh.net.vn" +  e.select("a").first().attr("href");
                    String title =  e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

//            .spec-news-sitebar >.trending.first
            Elements newsBreakingItemVetical = doc.select(".hlmoinhat > ul > li");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://giadinh.net.vn" +  r.select("a").first().attr("href");
                    String title =  r.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".listhltoptotal > .listhltop1");
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://giadinh.net.vn" +  contentBreaking.select("a").first().attr("href");
                String title =  contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select("a > img").attr("src");
                String contentText = contentBreaking.select("p.sapo").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentListTop = doc.select(".listhltop3 > .listhltop3ltop");
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://giadinh.net.vn" +  contentListTop.select("a").first().attr("href");
                String title =  contentListTop.select("a").first().attr("title");
                String thumb = contentListTop.select("a > img").attr("src");
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }
            Elements contentBreakingRight = doc.select(".listhltop3 > ul > li.spicepli");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://giadinh.net.vn" +  e.select("h3 > a").first().attr("href");
                    String title =  e.select("h3").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }
            Elements contentBreakingBottom = doc.select(".listhltop4> ul > li");
            for (Element e : contentBreakingBottom) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://giadinh.net.vn" +  e.select("a").first().attr("href");
                    String title =  e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception e2) {
                    // ghi log
                }
            }
            Elements contentItemsEle = doc.select(".listtotal1 > .showlist > ul > li");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://giadinh.net.vn" +  e.select("a").first().attr("href");
                    String title =  e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    String contentText = e.select("p").first().text();
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
        appModel.setSource("Nguồn: Gia Đình");
        if (pageUrl == "http://giadinh.net.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("http://giadinh.net.vn/phap-luat/")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("http://giadinh.net.vn/bon-phuong/")) {
            appModel.setCatid(catNews.the_gioi);
        }
//        if (pageUrl.contains("http://giadinh.net.vn/the-thao/")) {
//            appModel.setCatid(catNews.the_thao);
//        }
        if (pageUrl.contains("http://giadinh.net.vn/xa-hoi/")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("http://giadinh.net.vn/thi-truong/")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("http://giadinh.net.vn/gia-dinh-hi-tech/")) {
            appModel.setCatid(catNews.cong_nghe);
        }

//        if (pageUrl.contains("http://giadinh.net.vn/Chuyen-dong-van-hoa/")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("http://giadinh.net.vn/giai-tri/")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("http://giadinh.net.vn/giao-duc/")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("http://giadinh.net.vn/y-te/")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("http://giadinh.net.vn/o/")) {
            appModel.setCatid(catNews.nha_dat);
        }
//        if (pageUrl.contains("http://giadinh.net.vn/the-gioi-phuong-tien/")) {
//            appModel.setCatid(catNews.xe_co);
//        }

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
                    String contentT = doc.select(".detail-sp").text();
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
                    datePost = doc.select(".title-detail > p > span").text();
//                    if (datePost.isEmpty()) {
//                        datePost = doc.select(".detail-timer").text();
//                    }
                } catch (Exception e) {

                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT"));
                Pattern p = Pattern.compile("Ngày\\s([0-9]{1,2})\\sTháng\\s([0-9]{1,2}),\\s([0-9]{4}).*([0-9]{2}:[0-9]{2})\\s(PM|AM)");
                Matcher m = p.matcher(datePost);
                if (m.find()) {
                    try {
                        String date = m.group(1) +"/" + m.group(2) + "/" + m.group(3) + " " + m.group(4) + " " + m.group(5);
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
            try {
                contentRaw = doc.select(".content-new").toString();
//                if (contentRaw.isEmpty()) {
//                    contentRaw = doc.select(".detail-content").toString();
//                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

            String bottomNews = doc.select("h4.subbottomnew").toString().replaceAll("(\\s{2,1000}|\\n)", "");
            String releasted = doc.select(".VCSortableInPreviewMode[type=RelatedOneNews]").toString().replaceAll("(\\s{2,1000}|\\n)", "");;
            contentRaw = contentRaw.replaceAll(bottomNews, "");
            contentRaw = contentRaw.replaceAll(releasted, "");

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
                    imgThumb = doc.select(".content-new > * img").first().attr("src");
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
                Elements hinhAnh = doc.select(".content-new > .VCSortableInPreviewMode[type=photo]");
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
                    String LinkAnh = "<p><img src=\"" + el.select("div > img").attr("src") + "\" /></p>";
                    String motaAnh = "<p><em>" + el.select(".PhotoCMS_Caption").text() + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                }
            }
            try {
                Elements iframeVideos = doc.select(".content-new > .VCSortableInPreviewMode[type=VideoStream]");
                for (Element e : iframeVideos) {
                    Pattern p = Pattern.compile("vid=(giadinh\\/.*?.(mp4|mov))");
                    Matcher m = p.matcher(e.toString());
                    if (m.find()) {
                        try {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo("http://video.mediacdn.vn/" + m.group(1));
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + "http://video.mediacdn.vn/" + m.group(1) + "\"> </video>");
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
