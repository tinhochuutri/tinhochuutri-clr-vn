package trung.l5.crawler.reader.vnexpress;

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
import trung.l5.crawler.model.enums.Types;
import trung.l5.crawler.reader.IReader;
import trung.l5.crawler.schedules.vnexpress.VnExpressCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class VnExpressReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(VnExpressCrawlerItemListJob.class);

    public VnExpressReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();
        pages.add("https://vnexpress.net");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/thoi-su-p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/phap-luat-p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/the-gioi-p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/kinh-doanh/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/giai-tri/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/giao-duc-p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/suc-khoe";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/oto-xe-may-p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/doi-song/nha-p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/so-hoa/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vnexpress.net/the-thao/p" + i;
            pages.add(currentPage);
        }

        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();
        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://vnexpress.net") {
            Elements breakingNews = doc.select(".featured.container > article");
            try {
                Thread.sleep(1000);
                String sourceUrl = breakingNews.select("a").first().attr("href");
                String title = breakingNews.select("a").first().attr("title");
                String thumb = breakingNews.select(".thumb_big > a > img").attr("data-original");
                String contentText = breakingNews.select("p[class=description]").text();
                if (!sourceUrl.contains("video.vnexpress.net") && !sourceUrl.contains("vnexpress.net/interactive") && !sourceUrl.contains("startup.vnexpress.net") && !sourceUrl.contains("vnexpress.net/longform")) {
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    appModel.setContentText(contentText);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                }

            } catch (Exception e2) {
                // ghi log
            }

            Elements breakingNewsChild = doc.select("ul#list_sub_featured > li");
            for (Element e : breakingNewsChild) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    if (!sourceUrl.contains("video.vnexpress.net") && !sourceUrl.contains("vnexpress.net/interactive") && !sourceUrl.contains("startup.vnexpress.net") && !sourceUrl.contains("vnexpress.net/longform")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        // // // // // appModel.setThumb("");
                        appModel.setContentText("");
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);

                    }

                } catch (Exception e2) {
                    // ghi log
                }
            }
        } else {
            Elements featureNews = doc.select("section.featured.container > article");
            for (Element e : featureNews) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select(".thumb_big > a > img").attr("data-original");
                    String contentText = e.select("p[class=description]").text();
                    if (!sourceUrl.contains("video.vnexpress.net") && !sourceUrl.contains("vnexpress.net/interactive") && !sourceUrl.contains("startup.vnexpress.net") && !sourceUrl.contains("vnexpress.net/longform")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        appModel.setThumb(thumb);
                        appModel.setContentText(contentText);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);

                    }

                } catch (Exception e2) {
                    // ghi log
                }
            }
            Elements scrollNews = doc.select(".sub_featured > ul > li");
            for (Element e : scrollNews) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
//				String thumb = e.select(".thumb_big > a > img").attr("data-original");
                    String contentText = e.select("p[class=description]").text();
                    if (!sourceUrl.contains("video.vnexpress.net") && !sourceUrl.contains("vnexpress.net/interactive") && !sourceUrl.contains("startup.vnexpress.net") && !sourceUrl.contains("vnexpress.net/longform")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        // // // // // appModel.setThumb("");
                        appModel.setContentText(contentText);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);

                    }

                } catch (Exception e2) {
                    // ghi log
                }
            }
            Elements contentItemsEle = doc.select("section.sidebar_1 > article[class=list_news]");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("div[class=thumb_art] > a > img").attr("data-original");
                    String contentText = e.select("p[class=description]").text();
                    if (!sourceUrl.contains("video.vnexpress.net") && !sourceUrl.contains("vnexpress.net/interactive") && !sourceUrl.contains("startup.vnexpress.net") && !sourceUrl.contains("vnexpress.net/longform")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        appModel.setThumb(thumb);
                        appModel.setContentText(contentText);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);

                    }

                } catch (Exception e2) {
                    // ghi log
                }
            }
        }

        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: VnExpress");
        if (pageUrl == "https://vnexpress.net") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://vnexpress.net/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://vnexpress.net/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://vnexpress.net/thoi-su")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://vnexpress.net/kinh-doanh")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://vnexpress.net/so-hoa")) {
            appModel.setCatid(catNews.cong_nghe);
        }
        if (pageUrl.contains("https://vnexpress.net/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://vnexpress.net/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://vnexpress.net/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://vnexpress.net/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://vnexpress.net/doi-song/nha")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://vnexpress.net/oto-xe-may")) {
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
        try {
            Pattern p = Pattern.compile("(https:\\/\\/vnexpress.net\\/.*?.html)");
            Matcher m = p.matcher(itemUrl);
            if (m.find()) {
                try {
                    String abc = m.group(1);
                    appModel.setSourceLink(m.group(1));
                } catch (Exception e) {

                }
            }
        } catch (Exception er) {
        }
        Document doc = Jsoup.connect(itemUrl).get();
        try {
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";

            try {
                String text = doc.select(".sidebar_1 > p.description").text();
                appModel.setContentText(text);
            } catch (Exception e) {
            }
//            }
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String imgThumb = "";
            try {
                imgThumb = doc.select("table.tplCaption > tbody > tr > td > img").first().attr("src");

            } catch (Exception e) {
            }
            if (imgThumb.isEmpty()) {
                try {
                    imgThumb = doc.select("div.block_thumb_slide_show > img").first().attr("data-original");
                } catch (Exception e) {
                }
            }
            if (!imgThumb.isEmpty()) {
                appModel.setThumb(imgThumb);
            }

            Elements datePost = doc.select("meta[itemprop=datePublished]");

            if (datePost.isEmpty()) {
                datePost = doc.select("script[type=application/ld+json]");
                for (Element e : datePost) {
                    String date = e.toString().replace("-", "/");
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Pattern p = Pattern.compile("\"datePublished\":\\s?\\\"(\\d{4}\\/\\d{2}\\/\\d{2})[A-Z](\\d{2}:\\d{2}:\\d{2})");
                    Matcher m = p.matcher(date);
                    if (m.find()) {
                        try {
                            Date createdDate = dt.parse(m.group(1) + " " + m.group(2));
                            appModel.setCreatedDate(createdDate);

                        } catch (ParseException er) {
                            System.out.println("KHÔNG SET ĐƯỢC THỜI GIAN");
                            er.printStackTrace();
                        }
                    }

                }
            } else {
                for (Element e : datePost) {
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                    Date createdDate = dt.parse(e.attr("content"));
                    appModel.setCreatedDate(createdDate);


                }
            }


//            String datePost = doc.select("span.time").text();
//            if (datePost == "") {
//                datePost = doc.select(".right.timestamp").text();
//            }

            String contentRaw = doc.select(".content_detail.fck_detail").toString();
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n|<p class=\"for-reading-mode\">.*?<\\/p>)", " ");
            Elements hinhAnhThuong = doc.select("table");

            if (hinhAnhThuong.size() > 0) {
                for (Element el : hinhAnhThuong) {
                    String LinkAnh = el.select("tbody > tr > td > img").attr("src");
                    String chuThich = el.select("tbody > tr > td > img").attr("alt");
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", " ");
                    contentRaw = contentRaw.replace(elHinhAnh, "<p><img src=\"" + LinkAnh + "\"/></p>\n" + "<p><em>" + chuThich + "</em></p>");
                }
            }
            Elements hinhAnhScript = doc.select(".block_thumb_slide_show");
            if (hinhAnhScript.size() > 0) {
                for (Element el : hinhAnhScript) {
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", " ");
                    String LinkAnh = el.select("img").attr("data-original");
                    contentRaw = contentRaw.replace(elHinhAnh, "<p><img src=\"" + LinkAnh + "\"/></p>");

                }
            }

            contentRaw = contentRaw.replaceAll("(<article.*?>|</article>|<div*[^\\>]+>|<p[^\\>]+>)", "<p>");
            contentRaw = contentRaw.replaceAll("</div>", "</p>");
            contentRaw = contentRaw.replaceAll("(<span*[^\\>]+>|<\\/span>|<p><\\/p>|<p>\\s+}?<\\/p>|<script*[^\\>]+>.*?<\\/script>|<coccocgrammar><\\/coccocgrammar>|style=\".*?\"|<p>\\s?Tin liên quan.*?<\\/p>|<ins.*?<\\/ins>|<div id=\"box_splienquan\".*?<\\/div>|<figure.*?>|<\\/figure>|<figcaption.*?>|<\\/figcaption>| <video.*?<\\/video>| <!--.*?-->)", "");
            contentRaw = contentRaw.replaceAll("(&nbsp;|<br>|<ul.*?<\\/ul>|<p>\\s?Tin liên quan:.*?<\\/p>| <p>\\s+<\\/p>)", " ");
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
            Elements contentAAA = doc.select(".content_detail.fck_detail");
            appModel.setContentHtml(contentHtml);
//			String contentHtml = content + contentText + source + doc.select("section.sidebar_1 > article").html();
//			appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
