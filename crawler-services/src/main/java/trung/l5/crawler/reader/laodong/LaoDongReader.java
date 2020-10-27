package trung.l5.crawler.reader.laodong;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import trung.l5.crawler.schedules.laodong.LaoDongCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class LaoDongReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(LaoDongCrawlerItemListJob.class);

    public LaoDongReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();
        pages.add("https://laodong.vn");
        pages.add("https://laodong.vn/thoi-su");
        pages.add("https://laodong.vn/the-gioi");
        pages.add("https://laodong.vn/van-hoa");
        pages.add("https://laodong.vn/kinh-te");
//        pages.add("https://congnghe.tuoitre.vn");
        pages.add("https://laodong.vn/the-thao");
        pages.add("https://laodong.vn/giai-tri");
        pages.add("https://laodong.vn/phap-luat");
        pages.add("https://laodong.vn/giao-duc");
        pages.add("https://laodong.vn/suc-khoe");
        pages.add("https://laodong.vn/bat-dong-san");
        pages.add("https://laodong.vn/xe");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://laodong.vn") {
            Elements contentBreaking = doc.select("article.cover");
            try {
                Thread.sleep(1000);
                String sourceUrl = contentBreaking.select("a").first().attr("href");
                String thumb = contentBreaking.select("figure._thumb > img").attr("data-src");
                if (!sourceUrl.contains("https://laodong.vn/infographic")) {
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                }

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements newsBreakingItemHome = doc.select(".top-news > ul > li");
            for (Element e : newsBreakingItemHome) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    if (!sourceUrl.contains("https://laodong.vn/infographic")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        // appModel.setThumb("");
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    }
                } catch (Exception err) {

                }
            }
//            Elements newsBreakingItemHorizontal = doc.select(".home-highlight > ul > li");
//            for (Element r : newsBreakingItemHorizontal) {
//                try {
//                    Thread.sleep(1000);
//                    String sourceUrl = "https://laodong.vn" + r.select("a").first().attr("href");
//                    String title = r.select("a").first().attr("title");
//                    String thumb = r.select("img.img154x96").attr("src");
////                    String contentText = contentBreaking.select("p.sapo.need-trimline").text();
//                    AppModel appModel = new AppModel();
//                    appModel.setSourceLink(sourceUrl);
//                    appModel.setTitle(title);
//                    appModel.setThumb(thumb);
//                    appModel.setContentText("");
//                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
//                } catch (Exception err) {
//
//                }
//            }
        } else {

            Elements contentBreaking = doc.select("div.wrapper-feature > div.left-col > article.article-large");
            try {
                Thread.sleep(1000);
                String sourceUrl = contentBreaking.select("a").first().attr("href");
                if (!sourceUrl.contains("https://laodong.vn/infographic")) {
                    String thumb = contentBreaking.select("figure._thumb > img").attr("data-src");
                    String contentText = contentBreaking.select("p").text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                }
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }
            Elements contentBreakingRight = doc.select("div.right-col > div.most-read > ul > li");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    if (!sourceUrl.contains("https://laodong.vn/infographic")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        // appModel.setThumb("");
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    }
                } catch (Exception err) {

                }
            }
            Elements contentBreakingChild = doc.select("ul.list-feature");
            for (Element e : contentBreakingChild) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    if (!sourceUrl.contains("https://laodong.vn/infographic")) {
                        String thumb = e.select("figure._thumb > img").attr("data-src");
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setThumb(thumb);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    }
                } catch (Exception err) {

                }
            }
            Elements contentItemsEle = doc.select("ul.list-main-content.list-main-new > li");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    if (!sourceUrl.contains("https://laodong.vn/infographic")) {
                        String thumb = e.select("figure._thumb > img").attr("data-src");
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setThumb(thumb);
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
        appModel.setSource("Nguồn: Lao Động");
        if (pageUrl == "https://laodong.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://laodong.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://laodong.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://laodong.vn/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://laodong.vn/van-hoa")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://laodong.vn/thoi-su")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://laodong.vn/kinh-te")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://congnghe.tuoitre.vn")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("https://laodong.vn/van-hoa")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://laodong.vn/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://laodong.vn/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://laodong.vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://laodong.vn/bat-dong-san")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://laodong.vn/xe")) {
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
            String title = doc.select("h1").first().text();
            appModel.setTitle(title);
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText;
            String text = doc.select("p.abs").text();
            appModel.setContentText(text);
            contentText = "<p class=\'title\'>" + text + "</p>";

            String datePost = doc.select(".time.for-reading-mode > .f-datetime").text();
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//            dt.setTimeZone(TimeZone.getTimeZone("GMT"));
            Pattern p = Pattern.compile("([0-9]*\\/[0-9]*\\/\\d{4})(\\s\\|\\s)([0-9]{2}:[0-9]{2})");
            Matcher m = p.matcher(datePost);
            if (m.find()) {
                try {
                    Date createdDate = dt.parse(m.group(1) + " " + m.group(3));
//                    dt.format(createdDate);
                    appModel.setCreatedDate(createdDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String author = "<p><strong>" + doc.select("span.author").text() + "</strong></p>";
            Elements contentPost = doc.select(".article-content");
            String contentRaw = contentPost.toString();
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n|<p class=\"for-reading-mode\">.*?<\\/p>)", "");
            try {
                String imgThumb = doc.select(".imgCon > a > figure > img").first().attr("data-src");
                if (!imgThumb.isEmpty()) {
                    appModel.setThumb(imgThumb);
                    contentRaw = "<p><img src=\"" + imgThumb + "\"/></p>" + contentRaw;
                }
            } catch (Exception e) {
                try {
                    String imgThumb = doc.select(".wrapper-main-content > * figure > * img").first().attr("data-src");
                    appModel.setThumb(imgThumb);
                } catch (Exception er) {
                }
            }
            Elements video = contentPost.select("video");
            Boolean videoInContent = true;
            if (video.size() == 0) {
                video = doc.select("div.video-content-main > div.video > video");
                videoInContent = false;
            }
            if (video.size() > 0) {
                for (Element vid : video) {
                    Pattern v = Pattern.compile("<source src=\"(https:\\/\\/media.laodong.vn.*?\\.(mp4|mov))");
                    Matcher z = v.matcher(vid.toString());
                    if (z.find()) {
                        appModel.setIsVideo(true);
                        appModel.setLinkVideo(z.group(1));
                        if (videoInContent) {
                            String elVideo = vid.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(elVideo, "<video controls><source src=\"" + z.group(1) + "\"> </video>");
                        } else {
                            contentRaw = "<p> <video controls><source src=\"" + z.group(1) + "\"> </video></p>";
                        }

                    }
                }
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
            Elements hinhAnh = contentPost.select("figure");
            if (hinhAnh.size() > 0) {
                for (Element el : hinhAnh) {
                    String linkAnh = el.select("img").attr("src");
                    String chuThich = el.select("figcaption.image-caption").text();
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(elHinhAnh, "<p><img src=\"" + linkAnh + "\"/></p>" + "\n" + "<p><em>" + chuThich + "</em></p>");
                }
            }

            contentRaw = contentRaw.replaceAll("(<article.*?>|</article>|<div*[^\\>]+>|<p[^\\>]+>)|style=\".*?\"", "<p>");
            contentRaw = contentRaw.replaceAll("</div>", "</p>");
            contentRaw = contentRaw.replaceAll("(<span*[^\\>]+>|<\\/span>|<p><\\/p>|<script*[^\\>]+>.*?<\\/script>|<coccocgrammar><\\/coccocgrammar>)", "");
            contentRaw = contentRaw.replaceAll("(&nbsp;|<br>)", " ");
            appModel.setContentRaw(contentRaw);
            String contentHtml = "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n" +
                    "</head>\n" +

                    content + "\n" + source + "\n" + contentText
                    + "\n<div class='content-detail'>\n" +
                    "  <div>\n" + contentRaw + "\n" + author + "\n" + " </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "</html>";
            String contentPostAAAAAAAAAA = doc.select(".article-content").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }


}
