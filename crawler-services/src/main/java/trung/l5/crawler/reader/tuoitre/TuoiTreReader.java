package trung.l5.crawler.reader.tuoitre;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import trung.l5.crawler.model.enums.Types;
import trung.l5.crawler.reader.IReader;
import trung.l5.crawler.schedules.tuoitre.TuoiTreCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class TuoiTreReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(TuoiTreCrawlerItemListJob.class);

    public TuoiTreReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();
        pages.add("https://tuoitre.vn");
        pages.add("https://tuoitre.vn/thoi-su");
        pages.add("https://tuoitre.vn/the-gioi");
        pages.add("https://tuoitre.vn/van-hoa");
        pages.add("https://tuoitre.vn/kinh-doanh");
        pages.add("https://congnghe.tuoitre.vn");
        pages.add("https://thethao.tuoitre.vn");
        pages.add("https://tuoitre.vn/giai-tri");
        pages.add("https://tuoitre.vn/phap-luat");
        pages.add("https://tuoitre.vn/giao-duc");
        pages.add("https://tuoitre.vn/suc-khoe");
        pages.add("https://nhadat.tuoitre.vn/nha-dat");
        pages.add("https://tuoitre.vn/xe");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://tuoitre.vn") {
            Elements contentBreaking = doc.select(".focus-first");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://tuoitre.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select("a > img.img440x275").attr("src");
                String contentText = contentBreaking.select("p.sapo.need-trimline").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements newsBreakingItemHome = doc.select("ul.scroll-pane > li");
            for (Element e : newsBreakingItemHome) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://tuoitre.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    // appModel.setThumb("");
                    appModel.setContentText("");
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            Elements newsBreakingItemHorizontal = doc.select(".list-news-focus > ul > li");
            for (Element r : newsBreakingItemHorizontal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://tuoitre.vn" + r.select("a").first().attr("href");
                    String title = r.select("a").first().attr("title");
                    String thumb = r.select("img.img154x96").attr("src");
//                    String contentText = contentBreaking.select("p.sapo.need-trimline").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    appModel.setContentText("");
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".focus-top.clearfix");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://tuoitre.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select(".focus-top.clearfix > .fl.focus-top-box-left > a > img").attr("data-src");
                String contentText = contentBreaking.select("p.sapo.need-trimline").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }
            Elements contentBreakingChild = doc.select("ul.focus-bottom-ul > li.focus-bottom-li");
            for (Element e : contentBreakingChild) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://tuoitre.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("li.focus-bottom-li > a > img").attr("data-src");
//                String contentText = e.select("p.sapo.need-trimline").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    appModel.setContentText("");
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            Elements contentItemsEle = doc.select("ul.list-news-content > li.news-item");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://tuoitre.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("li.news-item > a > img").attr("data-src");
                    String contentText = e.select("p.sapo.need-trimline").text();
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
        appModel.setSource("Nguồn: Tuổi Trẻ");
        if (pageUrl == "https://tuoitre.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://tuoitre.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://tuoitre.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://thethao.tuoitre.vn")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://tuoitre.vn/thoi-su")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://tuoitre.vn/kinh-doanh")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://congnghe.tuoitre.vn")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("https://tuoitre.vn/van-hoa")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://tuoitre.vn/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://tuoitre.vn/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://tuoitre.vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://nhadat.tuoitre.vn/nha-dat")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://tuoitre.vn/xe")) {
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

            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText;
            if (appModel.getContentText() == "") {
                String text = doc.select(".main-content-body > h2.sapo").text();
                appModel.setContentText(text);
                contentText = "<p class=\'title\'>" + text + "</p>";

            } else {
                contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            }
            String imgThumb = doc.select("img[type=photo]").first().attr("src");
            if (!imgThumb.isEmpty()) {
                appModel.setThumb(imgThumb);
            }
//            contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String author = "<p><strong>" + doc.select(".author").text() + "</strong></p>";
            String datePost = doc.select("div.info-right > div.subinfo.clearfix > span.datetime").attr("title");
            SimpleDateFormat dt = null;
            if (datePost == "") {
                datePost = doc.select("div.date-time").text();
                if (datePost == "") {
                    datePost = doc.select("span.date").text();
                }

                dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            } else {
                dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            }
            Pattern p = Pattern.compile("([0-9]*\\/[0-9]*\\/\\d{4})(\\s)([0-9]{2}:[0-9]{2})");
            Matcher m = p.matcher(datePost);
            if (m.find()) {
                try {
                    Date createdDate = dt.parse(m.group(1) + " " + m.group(3));
                    appModel.setCreatedDate(createdDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String contentRaw = doc.select("div.main-content-body > div.content > *:not(div[type=\"RelatedOneNews\"])").toString();
            Elements video = doc.select("div[type=\"VideoStream\"]");
            Elements hinhAnh = doc.select("div.main-content-body > div.content > *:not(div[type=\"RelatedOneNews\"]) img");
            Elements chuThich = doc.select("div.main-content-body > div.content > *:not(div[type=\"RelatedOneNews\"])>.PhotoCMS_Caption > p");
            for (Element vid : video) {
                Pattern v = Pattern.compile("<div .*vid\\=(tuoitre\\/.*.(mp4|mov))(.*\\>)");
                Matcher z = v.matcher(vid.toString());
                if (z.find()) {
                    appModel.setIsVideo(true);
                    appModel.setLinkVideo("https://hls.tuoitre.vn/" + z.group(1));
                    contentRaw = contentRaw.replace(vid.toString(), "<video controls><source src=\"https://hls.tuoitre.vn/" + z.group(1) + "\"> </video>");
                }
            }
            for (Element el : hinhAnh) {
                String LinkAnh = el.attr("src");
                contentRaw = contentRaw.replace(el.toString(), "<img src=\"" + LinkAnh + "\">");
            }
            for (Element cap : chuThich) {
                contentRaw = contentRaw.replace(cap.toString(), "<em>" + cap.text() + "</em>");
            }

            contentRaw = contentRaw.replaceAll("(<article.*?>|</article>|<div*[^\\>]+>|<p[^\\>]+>)", "<p>");
            contentRaw = contentRaw.replaceAll("</div>", "</p>");
            contentRaw = contentRaw.replaceAll("(<span*[^\\>]+>|<\\/span>|<p><\\/p>|<script*[^\\>]+>.*?<\\/script>|<coccocgrammar><\\/coccocgrammar>)", "");
            contentRaw = contentRaw.replaceAll("(&nbsp;|<br>)", " ");
            contentRaw = contentRaw.replaceAll("style=\".*?\"", "");
            appModel.setContentRaw(contentRaw);
            String contentHtml = "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n" +
                    "</head>\n" +

                    content + "\n" + source + "\n" + contentText
                    + "\n<div class='content-detail'>\n" +
                    "  <div>\n" + contentRaw + "\n" + author + " </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "</html>";
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }


}
