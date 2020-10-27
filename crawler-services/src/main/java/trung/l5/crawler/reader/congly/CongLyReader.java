package trung.l5.crawler.reader.congly;

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
import trung.l5.crawler.schedules.tienphong.TienPhongCrawlerListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class CongLyReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(TienPhongCrawlerListJob.class);

    public CongLyReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();

        pages.add("https://congly.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/xa-hoi/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/phap-luat/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/the-gioi/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/giai-tri/van-hoa/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/kinh-doanh/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/the-thao/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/giai-tri/thoi-trang/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/giai-tri/phim/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/giao-duc/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/suc-khoe/trang-" + i + "/";
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://congly.vn/xe/trang-" + i + "/";
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/bat-dong-san/trang-" + i + "/";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://congly.vn/cong-nghe/trang-" + i + "/";
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://congly.vn") {
            Elements bigNews = doc.select(".highlight-fnews");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://congly.vn" + bigNews.select("a").first().attr("href");
                String title = bigNews.select("a").first().attr("title");
                String thumb = bigNews.select("a > img").attr("src");
                String contentText = bigNews.select(".highlight-hfnews-description").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItem = doc.select(".highlight-snews-list > .highlight-snews-item");
            for (Element e : breakingNewsItem) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://congly.vn" + e.select("a").first().attr("href");
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
            Elements newsBreakingItemVetical = doc.select(".viewport > .overview > ul > li");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://congly.vn" + r.select("a").first().attr("href");
                    String title = r.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".content-left > .news-hleft");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://congly.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select("a > img").attr("src");
                String contentText = contentBreaking.select(".news-hleft-description").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentBreakingRight = doc.select(".content-left > .news-hright > ul > li");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://congly.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }
//            Elements contentBreakingBottom = doc.select(".news-listitem > .news-list-oneitem");
//            for (Element e : contentBreakingBottom) {
//                try {
//                    Thread.sleep(1000);
//                    String sourceUrl = "https://congly.vn" + e.select("a").first().attr("href");
//                    String title = e.select("a").first().attr("title");
//                    String thumb = e.select("a > img").attr("src");
//                    AppModel appModel = new AppModel();
//                    appModel.setSourceLink(sourceUrl);
//                    appModel.setTitle(title);
//                    appModel.setThumb(thumb);
//                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
//
//                } catch (Exception err) {
//
//                }
//            }
            Elements contentItemsEle = doc.select(".news-listitem > .news-list-oneitem");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://congly.vn" + e.select("> a").first().attr("href");
                    String title = e.select("> a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    String contentText = e.select(".news-listroneitem > .news-listroneitem-description").text();
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
        appModel.setSource("Nguồn: Công Lý");
        if (pageUrl == "https://congly.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://congly.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://congly.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://congly.vn/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://congly.vn/xa-hoi")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://congly.vn/kinh-doanh")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://congly.vn/cong-nghe/")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("https://congly.vn/giai-tri/van-hoa")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://congly.vn/giai-tri/thoi-trang") || pageUrl.contains("https://congly.vn/giai-tri/phim")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://congly.vn/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://congly.vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://congly.vn/bat-dong-san")) {
            appModel.setCatid(catNews.nha_dat);
        }
//        if (pageUrl.contains("https://congly.vn/xe")) {
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
                    String contentT = doc.select(".news-detail-summary").text();
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
                    datePost = doc.select(".date-top").text();
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
            String contentRaw = doc.select(".news-dscontent").toString();
            if (!contentRaw.isEmpty()) {
                try {
                    String imgThumb = doc.select(".news-dscontent >* img").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                }



                Elements hinhAnh = doc.select(".news-dscontent >* img");
                if(hinhAnh.isEmpty()) {
                    hinhAnh = doc.select(".news-dscontent >* input");
                }
                for (Element el : hinhAnh) {
                    String LinkAnh = "<p><img src=\"" + el.attr("src") + "\" /></p>";
//                    String motaAnh = "<p><em>" + el.attr("alt") + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", " ");
                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh);
                }

            } else {
                contentRaw = doc.select("#player_video_main").toString();
            }


            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
//            try {
//                Elements iframeVideos = doc.select(".cms-video");
//                for (Element e : iframeVideos) {
//                    String linkIFrame = e.attr("src");
//                    linkIFrame = linkIFrame.replaceAll("%2f", "/");
//                    linkIFrame = linkIFrame.replaceAll("%3a", ":");
//                    Pattern p = Pattern.compile("(https:\\/\\/streaming.*?(.mp4|.mov))");
//                    Matcher m = p.matcher(linkIFrame);
//                    if (m.find()) {
//                        try {
//                            appModel.setIsVideo(true);
//                            appModel.setLinkVideo(m.group(1));
//                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", " ");
//                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(1) + "\"> </video>");
//                        } catch (Exception err) {
//                        }
//                    }
//                }
//            } catch (Exception e) {
//            }

//            String regex;
//            Pattern author = Pattern.compile("<p class=\"article-author cms-author\">((.*|\\n))?<\\/p>");
//            Matcher mAu = author.matcher(contentRaw);


//            contentRaw = contentRaw.replaceAll("(<span.*?cms-author.*?>)", "<strong>");
            contentRaw = contentRaw.replaceAll("<span class=\"fig\">.*?<\\/span>", "");
            contentRaw = contentRaw.replaceAll("(<b\\s?style.*?>|<b>)", "<strong>");
            contentRaw = contentRaw.replaceAll("<\\/b>", "</strong>");
            contentRaw = contentRaw.replaceAll("&nbsp;", " ");
            contentRaw = contentRaw.replaceAll("(<div*[^\\>]+>|<address>)", "<p>");
            contentRaw = contentRaw.replaceAll("(</div>|</address>)", "</p>");
            contentRaw = contentRaw.replaceAll("<p[^\\>]+>", "<p>");
            contentRaw = contentRaw.replaceAll("((<span*[^\\>]+>))", "");
            contentRaw = contentRaw.replaceAll("</span>", "");
            contentRaw = contentRaw.replaceAll("</a>", "");
            contentRaw = contentRaw.replaceAll("<p></p>", "");
            contentRaw = contentRaw.replaceAll("&amp;", "&");
            contentRaw = contentRaw.replaceAll("(<p><iframe.*?<\\/p>|<script.*?<\\/script>|<br>|<blockquote.*?>|<\\/blockquote>|<hr>)", "");
            contentRaw = contentRaw.replaceAll("<i>", "<em>");
            contentRaw = contentRaw.replaceAll("<\\/i>", "</em>");
            contentRaw = contentRaw.replaceAll("((<a.*?>)|(<table.*<td>)|(<\\/td?>.*<\\/table>))", "");
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
