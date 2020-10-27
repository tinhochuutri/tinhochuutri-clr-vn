package trung.l5.crawler.reader.vietnamnet;

import java.io.IOException;
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
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.model.TypeCategory;
import trung.l5.crawler.model.enums.Types;
import trung.l5.crawler.reader.IReader;
import trung.l5.crawler.schedules.dantri.DanTriCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class VietNamNetReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(DanTriCrawlerItemListJob.class);

    public VietNamNetReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();

        pages.add("https://vietnamnet.vn");
        pages.add("https://vietnamnet.vn/vn/thoi-su");
        pages.add("https://vietnamnet.vn/vn/phap-luat");
        pages.add("https://vietnamnet.vn/vn/the-gioi");
        pages.add("https://vietnamnet.vn/vn/doi-song");
        pages.add("https://vietnamnet.vn/vn/kinh-doanh");
        pages.add("https://vietnamnet.vn/vn/the-thao/bong-da-viet-nam");
        pages.add("https://vietnamnet.vn/vn/giai-tri");
        pages.add("https://vietnamnet.vn/vn/giao-duc");
        pages.add("https://vietnamnet.vn/vn/suc-khoe");
        pages.add("https://vietnamnet.vn/vn/oto-xe-may");
        pages.add("https://vietnamnet.vn/vn/bat-dong-san");
        pages.add("https://vietnamnet.vn/vn/cong-nghe");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://vietnamnet.vn") {
            Elements bigNews = doc.select(".TopNew.va-top > .w-440.d-ib > .top-one");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://vietnamnet.vn" + bigNews.select("a").first().attr("href");
                String title = bigNews.select("a > img").first().attr("alt");
                String thumb = bigNews.select("a > img").attr("src");
                String contentText = bigNews.select("div.lead.m-t-5").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItem = doc.select(".TopNew.va-top > .w-440.d-ib > ul.widht-list-images > li");
            for (Element e : breakingNewsItem) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vietnamnet.vn" + e.select("a").first().attr("href");
                    String title = e.select("a > img").first().attr("alt");
                    String thumb = e.select("a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            Elements newsBreakingItemVetical= doc.select(".nano.has-scrollbar >.nano-content.description > ul > li");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vietnamnet.vn" + r.select("a").first().attr("href");
                    String title = r.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select("div.top-one-cate");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://vietnamnet.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a > img").first().attr("alt");
                String thumb = contentBreaking.select("a > img").attr("src");
                String contentText = contentBreaking.select(".lead.m-t-5").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentItemsEle = doc.select(".list-content > .item");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vietnamnet.vn" + e.select("a").first().attr("href");
                    String title = e.select("a > img").first().attr("alt");
                    String thumb = e.select("a > img").attr("src");
                    String contentText = e.select(".lead").text();
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
        appModel.setSource("Nguồn: VietNamNet");
        if (pageUrl == "https://vietnamnet.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/thoi-su")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/kinh-doanh")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/cong-nghe")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("https://vietnamnet.vn/vn/doi-song")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/bat-dong-san")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://vietnamnet.vn/vn/oto-xe-may")) {
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
            if(appModel.getContentText() == null || appModel.getContentText() == "") {
                try {
                    String contentT = doc.select(".ArticleContent > .ArticleLead > h2").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {}

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            try {
                String imgThumb = doc.select("td.image > img").first().attr("src");
                if (!imgThumb.isEmpty()) {
                    appModel.setThumb(imgThumb);
                }
            } catch (Exception e) {
            }

            try {
                String datePost = doc.select(".ArticleDate").text();
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
            String contentRaw = doc.select(".ArticleContent").toString();
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n|<p class=\"for-reading-mode\">.*?<\\/p>)", " ");
            String relatedPost = doc.select(".ArticleContent > .article-relate").toString().replaceAll("(\\s{2,1000}|\\n)", " ");
            String postLienQuan = doc.select(".ArticleContent > .inner-article").toString().replaceAll("(\\s{2,1000}|\\n)", " ");
            String postDecs = doc.select(".ArticleContent > .ArticleLead").toString().replaceAll("(\\s{2,1000}|\\n)", " ");

            contentRaw = contentRaw.replace(relatedPost, "");
            contentRaw = contentRaw.replace(postLienQuan, "");
            contentRaw = contentRaw.replace(postDecs, "");
            Elements hinhAnh = doc.select("table.ImageBox");
            for (Element el : hinhAnh) {
                String LinkAnh = "<p><img src=\"" + el.select("td.image > img").attr("src") + "\" /></p>";
                String motaAnh = "<p><em>" + el.select(" tbody > tr > td.image_desc").text() + "</em></p>";
                String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", " ");
                contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
            }
            try {
                Elements iframeVideos = doc.select(".ArticleContent > p > iframe");
                for (Element e : iframeVideos) {
                    String linkIFrame = e.attr("src");
                   try {
                       Document docFrame = Jsoup.connect(linkIFrame).get();
                       String contentFrame = docFrame.toString();
                       Pattern p = Pattern.compile("'(https.*?.(mp4|mov))'");
                       Matcher m = p.matcher(contentFrame);
                       if (m.find()) {
                           try {
                               appModel.setIsVideo(true);
                               appModel.setLinkVideo(m.group(1));
                               String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", " ");
                               contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(1) + "\"> </video>");
                           } catch (Exception err) {
                           }
                       }

                   } catch (Exception err) {}
                }
            } catch (Exception e) {
            }


            contentRaw = contentRaw.replaceAll("&nbsp;", " ");
            contentRaw = contentRaw.replaceAll("(<div*[^\\>]+>|<address>)", "<p>");
            contentRaw = contentRaw.replaceAll("(</div>|</address>)", "</p>");
            contentRaw = contentRaw.replaceAll("<p[^\\>]+>", "<p>");
            contentRaw = contentRaw.replaceAll("<span*[^\\>]+>", "");
            contentRaw = contentRaw.replaceAll("</span>", "");
            contentRaw = contentRaw.replaceAll("<p></p>", "");
            contentRaw = contentRaw.replaceAll("<p><iframe.*?<\\/p>", "");
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
