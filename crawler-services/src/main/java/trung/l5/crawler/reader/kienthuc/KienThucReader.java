package trung.l5.crawler.reader.kienthuc;

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
import trung.l5.crawler.schedules.kienthuc.KienThucCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class KienThucReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(KienThucCrawlerItemListJob.class);

    public KienThucReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("https://kienthuc.net.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/xa-hoi/?page=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://kienthuc.net.vn/phap-luat/?page=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/the-gioi/?page=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://kienthuc.net.vn/Chuyen-dong-van-hoa/?page=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/tieu-dung/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/the-thao/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/giai-tri/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/tuyen-sinh/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/khoe-dep/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/lan-banh/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/nha-dat/?page=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://kienthuc.net.vn/cong-nghe/?page=" + i;
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://kienthuc.net.vn") {
            Elements bigNews = doc.select(".highlight_left .story_lv1");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://kienthuc.net.vn" + bigNews.select("* a").first().attr("href");
                String title = bigNews.select("p.title").text();
                String thumb = bigNews.select("* a > img").first().attr("src");
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItemHoriztal = doc.select(".top article");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://kienthuc.net.vn" + e.select("* a").first().attr("href");
                    String title = e.select("p.title").text();
                    String thumb = e.select("* a > img").first().attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

//            .spec-news-sitebar >.trending.first
            Elements newsBreakingItemVetical = doc.select(".highlight_right ul li");
            for (Element e : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://kienthuc.net.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".cat-highligh .story");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://kienthuc.net.vn" + contentBreaking.select(".title > a").first().attr("href");
                String title = contentBreaking.select(".title").text();
                String thumb = contentBreaking.select(".thumbnail > * img").first().attr("src");
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentBreakingRight = doc.select(".cat-listnews article.story");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://kienthuc.net.vn" + e.select(".title > a").first().attr("href");
                    String title = e.select(".title").text();
                    String thumb = e.select(".thumbnail > * img").first().attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }

        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: Kiến Thức");
        if (pageUrl == "https://kienthuc.net.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
//        if (pageUrl.contains("https://kienthuc.net.vn/phap-luat/")) {
//            appModel.setCatid(catNews.phap_luat);
//        }
        if (pageUrl.contains("https://kienthuc.net.vn/the-gioi/")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/the-thao/")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/xa-hoi/")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/tieu-dung/")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/cong-nghe/")) {
            appModel.setCatid(catNews.cong_nghe);
        }

//        if (pageUrl.contains("https://kienthuc.net.vn/Chuyen-dong-van-hoa/")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("https://kienthuc.net.vn/giai-tri/")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/tuyen-sinh/")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/khoe-dep/")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/nha-dat/")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://kienthuc.net.vn/lan-banh/")) {
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
                    String contentT = doc.select(".cms-desc").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {
                }

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            Elements authorRaw = doc.select(".author");
//            if (authorRaw.isEmpty()) {
//                authorRaw = doc.select(".detail-auth");
//            }
            String author = "<p><strong>" + authorRaw.text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select("time").text();
                    if(datePost.isEmpty()) {
                        datePost = doc.select(".article-info .meta").text();
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
                contentRaw = doc.select(".cms-body").toString();
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

            try {
                Elements qc = doc.select(".zone");
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
                    imgThumb = doc.select(".cms-body table.contentimg > * img").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                        imgThumb = doc.select("#gallery > * img").first().attr("src");

                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                }
//                    try {
//                        if (imgThumb.isEmpty()) {
//                            imgThumb = doc.select(".detail-content >* .img > img").first().attr("src");
//                        }
//                    } catch (Exception er) {
//                    }

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
                try {
                    Elements hinhAnh = doc.select(".cms-body table.contentimg");
                    for (Element el : hinhAnh) {
                        String LinkAnh = "<p><img src=\"" + el.select("* img").attr("src") + "\" /></p>";
                        String motaAnh = "<p><em>" + el.select("* tr").get(1).text() + "</em></p>";
                        String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                    }
                } catch (Exception e) {
                }
                try {
                    Elements hinhAnh2 = doc.select(".cms-body .picitem");
                    for (Element el : hinhAnh2) {
                        String LinkAnh = "<p><img src=\"" + el.select("img").attr("src") + "\" /></p>";
                        String motaAnh = "<p><em>" + el.select(".gallery-title").text() + "</em></p>";
                        String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                    }
                } catch (Exception e) {
                }
            }
            try {
                Elements iframeVideos = doc.select(".contentvideo");
                for (Element e : iframeVideos) {
                    Pattern p = Pattern.compile("'(http.*?.(mp4|mov))");
                    Matcher m = p.matcher(e.select("* script").toString());
                    if (m.find()) {
                        try {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo(m.group(1));
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(1) + "\"> </video>");
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
            contentRaw = contentRaw.replace(" <a href=\"javascript:galback();\" class=\"gallery-arrow gallery-arrow-prev\"></a>", "");
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
            String contentAAA = doc.select(".cms-body").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
