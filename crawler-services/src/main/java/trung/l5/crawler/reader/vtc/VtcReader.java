package trung.l5.crawler.reader.vtc;

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
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.model.TypeCategory;
import trung.l5.crawler.reader.IReader;
import trung.l5.crawler.schedules.vtc.VtcCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class VtcReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(VtcCrawlerItemListJob.class);

    public VtcReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();

        pages.add("https://vtc.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/xa-hoi-28.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/phap-luat-32.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/the-gioi-30.html/p" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://vtc.vn/van-hoa-.html/p" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/kinh-te-29.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/the-thao-34.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/giai-tri-33.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/giao-duc-31.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/suc-khoe-35.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/oto-xe-may-37.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/dia-oc-52.html/p" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vtc.vn/khoa-hoc-cong-nghe-82.html/p" + i;
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://vtc.vn") {
            Elements bigNews = doc.select(".newest");
            try {
                Thread.sleep(1000);
                String sourceUrl = bigNews.select("figure > a").first().attr("href");
                String title = bigNews.select("figure > a").first().attr("title");
                String thumb = bigNews.select("figure > a > img").attr("src");
//                String contentText = bigNews.select(".summary").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
//                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItem = doc.select(".top-highlight > article.later");
            for (Element e : breakingNewsItem) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("figure > a").first().attr("href");
                    String title = e.select("figure > a").first().attr("title");
                    String thumb = e.select("figure > a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            Elements newsBreakingItemVetical = doc.select(".tab-contents > * article > * h4");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = r.select(" a").first().attr("href");
                    String title = r.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentItemsEle = doc.select(".col-cate > article");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("figure > a").first().attr("href");
                    String title = e.select("figure > a").first().attr("title");
                    String thumb = e.select("figure > a > img").attr("src");
                    String contentText = e.select("* .excerpt").text();
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
        appModel.setSource("Nguồn: VTC News");
        if (pageUrl == "https://vtc.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://vtc.vn/phap-luat") || pageUrl.contains("https://vtc.vn/video-clip/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://vtc.vn/the-gioi") || pageUrl.contains("https://vtc.vn/video-clip/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://vtc.vn/the-thao") || pageUrl.contains("https://vtc.vn/video-clip/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://vtc.vn/xa-hoi-28") || pageUrl.contains("https://vtc.vn/video-clip/xa-hoi-28")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://vtc.vn/kinh-te") || pageUrl.contains("https://vtc.vn/video-clip/kinh-te")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://vtc.vn/khoa-hoc-cong-nghe")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("https://vtc.vn/van-hoa") || pageUrl.contains("https://vtc.vn/video-clip/van-hoa")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://vtc.vn/giai-tri") || pageUrl.contains("https://vtc.vn/video-clip/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://vtc.vn/giao-duc") || pageUrl.contains("https://vtc.vn/video-clip/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://vtc.vn/suc-khoe") || pageUrl.contains("https://vtc.vn/video-clip/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://vtc.vn/dia-oc") || pageUrl.contains("https://vtc.vn/video-clip/dia-oc")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://vtc.vn/oto-xe-may") || pageUrl.contains("https://vtc.vn/video-clip/oto-xe-may-37")) {
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
                    String contentT = doc.select("h2.single-excerpt").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {
                }

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";


            try {
                String datePost = "";
                try {
                    datePost = doc.select("time").text();
                } catch (Exception e) {
                }
//                if(datePost.isEmpty()) {
//                    try {
//                        datePost = doc.select("time").text();
//                    } catch (Exception e) {
//                    }
//                }
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT"));
                Pattern p = Pattern.compile("([0-9]*\\/[0-9]*\\/\\d{4})(\\s*)([0-9]{2}:[0-9]{2})");
                Matcher m = p.matcher(datePost);
                if (m.find()) {
                    try {
                        Date createdDate = dt.parse(m.group(1) + " " + m.group(3));
//                        dt.format(createdDate);
                        appModel.setCreatedDate(createdDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            }
            String contentRaw = "";
            contentRaw = doc.select("#content_detail").toString();
//            if(contentRaw.isEmpty()) {
//                try {
//                    contentRaw = doc.select("").toString();
//                } catch (Exception e){}
//            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
            if (!contentRaw.isEmpty()) {
                try {
                    String imgThumb = doc.select("figure.expNoEdit >img").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                }
                try {
                    Elements relatePost = doc.select("#content_detail > .block_with_img_left");
                    for (Element e : relatePost) {
                        String re = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(re, "");
                    }

                } catch (Exception e) {
                }
                try {
                    Elements singleTag = doc.select(".single-tags");
                    for (Element e : singleTag) {
                        String re = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(re, "");
                    }
                } catch (Exception e) {}
                try {
                    Elements lienquanPost = doc.select("div.expNoEdit");
                    for (Element e : lienquanPost) {
                        String re = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(re, "");
                    }
                } catch (Exception e) {}
                Elements hinhAnh = doc.select("figure.expNoEdit");
                for (Element el : hinhAnh) {
                    String LinkAnh = "<p><img src=\"" + el.select("img").attr("src") + "\" /></p>";
                    String motaAnh = "<p><em>" + el.select("figcaption").text() + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                }
            }

            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

            try {
                Elements iframeVideos = doc.select("#content_detail > * iframe");
                for (Element e : iframeVideos) {
                    String linkIFrame = e.attr("src");
                    Document contentIf = Jsoup.connect(linkIFrame).get();
                    Pattern p = Pattern.compile("(https:\\/\\/medi.*?.mp4)");
                    Matcher m = p.matcher(contentIf.toString());
                    if (m.find()) {
                        try {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo(m.group(1));
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(1) + "\"> </video>");
                        } catch (Exception err) {
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "");
                        }
                    }
                }
            } catch (Exception e) {
            }


//            contentRaw = contentRaw.replaceAll("(<span.*?cms-author.*?>)", "<strong>");
            contentRaw = contentRaw.replaceAll("<span class=\"fig\">.*?<\\/span>", "");
            contentRaw = contentRaw.replaceAll("(<b\\s?style.*?>|<b>)", "<strong>");
            contentRaw = contentRaw.replaceAll("<\\/b>", "</strong>");
            contentRaw = contentRaw.replaceAll("&nbsp;", " ");
            contentRaw = contentRaw.replaceAll("(<div*[^\\>]+>|<address>)", "<p>");
            contentRaw = contentRaw.replaceAll("(</div>|</address>)", "</p>");
            contentRaw = contentRaw.replaceAll("<p[^\\>]+>", "<p>");
            contentRaw = contentRaw.replaceAll("<span*[^\\>]+>", "");
            contentRaw = contentRaw.replaceAll("</span>", "");
            contentRaw = contentRaw.replaceAll("<p></p>", "");
            contentRaw = contentRaw.replaceAll("&amp;", "&");
            contentRaw = contentRaw.replaceAll("(<p><iframe.*?<\\/p>|<script.*?<\\/script>|<br>|<blockquote.*?>|<\\/blockquote>|<hr>)", "");
            contentRaw = contentRaw.replaceAll("<i>", "<em>");
            contentRaw = contentRaw.replaceAll("<\\/i>", "</em>");
            contentRaw = contentRaw.replaceAll("<footer.*?>", "<p><strong>");
            contentRaw = contentRaw.replaceAll("</footer>", "</p></strong>");
            contentRaw = contentRaw.replaceAll("(<a.*?>)|</a>", "");
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
