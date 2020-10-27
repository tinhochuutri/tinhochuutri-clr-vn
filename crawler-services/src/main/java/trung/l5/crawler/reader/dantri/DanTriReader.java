package trung.l5.crawler.reader.dantri;

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
import trung.l5.crawler.schedules.dantri.DanTriCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class DanTriReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(DanTriCrawlerItemListJob.class);

    public DanTriReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();

        pages.add("https://dantri.com.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/xa-hoi/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/phap-luat/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/the-gioi/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/van-hoa/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/kinh-doanh/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/the-thao/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/giai-tri/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/giao-duc-khuyen-hoc/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/suc-khoe/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/o-to-xe-may/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/bat-dong-san/trang-" + i + ".htm";
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://dantri.com.vn/suc-manh-so/trang-" + i + ".htm";
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://dantri.com.vn") {
            Elements newsBreakingItemHome = doc.select("div[data-boxtype=homenewsposition], li[data-boxtype=homenewsposition]");
            for (Element e : newsBreakingItemHome) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://dantri.com.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = "";
                    thumb = e.select("a > img").attr("src");
                    String contentText = "";
                    String contentText2 = e.select(".mt2.fon2.nbtcsapo").toString();
                    Pattern p = Pattern.compile("<span>(Dân trí|\\(Dân trí\\))<\\/span>&nbsp;(.*)\\.\\s");
                    Matcher m ;
                    if(!contentText2.isEmpty()) {
                        m = p.matcher(contentText2);
                        if (m.find()) {
                            contentText = m.group(1) + " " + m.group(2);
                        }
                    }

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    appModel.setContentText(contentText);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
//            Elements newsBreakingItemHorizontal = doc.select(".list-news-focus > ul > li");
//            for (Element r : newsBreakingItemHorizontal) {
//                try {
//                    Thread.sleep(1000);
//                    String sourceUrl = "https://dantri.com.vn" + r.select("a").first().attr("href");
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

            Elements contentBreaking = doc.select("div[data-boxtype=zonenewsposition]");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://dantri.com.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select("a > img").attr("src");
                String contentText = "";
                String contentText2 = contentBreaking.select(".fon5.wid255.fl").toString();

                AppModel appModel = new AppModel();
                Pattern p = Pattern.compile("<span>(Dân trí|\\(Dân trí\\))<\\/span>&nbsp;(.*)(.|\\n)\\s*");
                Matcher m = p.matcher(contentText2);
                if (m.find()) {
                    contentText = m.group(1) + " " + m.group(2);
                }
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentItemsEle = doc.select("div[data-boxtype=timelineposition]");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://dantri.com.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    AppModel appModel = new AppModel();
                    String contentText = "";
                    String contentText2 = e.select(".fon5.wid324.fl").toString();

                    Pattern p = Pattern.compile("<span>(Dân trí|\\(Dân trí\\))<\\/span>&nbsp;(.*)(.|\\n)\\s*");
                    Matcher m = p.matcher(contentText2);
                    if (m.find()) {
                        contentText = m.group(1) + " " + m.group(2);
                    }
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
        appModel.setSource("Nguồn: Dân Trí");
        if (pageUrl == "https://dantri.com.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://dantri.com.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://dantri.com.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://dantri.com.vn/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://dantri.com.vn/xa-hoi")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://dantri.com.vn/kinh-doanh")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://dantri.com.vn/suc-manh-so")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("https://dantri.com.vn/van-hoa")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("https://dantri.com.vn/giai-tri")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://dantri.com.vn/giao-duc-khuyen-hoc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://dantri.com.vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("ttps://dantri.com.vn/bat-dong-san")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://dantri.com.vn/o-to-xe-may")) {
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
        itemUrl = "https://dantri.com.vn/kinh-doanh/ts-nguyen-tri-hieu-co-he-thong-tin-nhiem-vay-tien-trong-5-phut-20190518084017836.htm";
        Document doc = Jsoup.connect(itemUrl).get();

        try {

            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "";
            if (appModel.getContentText() == "") {
                String contentText2 = doc.select(".sapo").toString();
//                appModel.setContentText(text);
//                contentText = "<p class=\'title\'>" + text + "</p>";


                Pattern p = Pattern.compile("<span>(Dân trí|\\(Dân trí\\))<\\/span>&nbsp;(.*)\\.\\s");
                Matcher m ;
                if(!contentText2.isEmpty()) {
                    m = p.matcher(contentText2);
                    if (m.find()) {
                        String text = m.group(2);
                        appModel.setContentText(text);
                        contentText = "<p class=\'title\'>" + text + "</p>";
                    } else {
                        appModel.setContentText(doc.select(".sapo").text());
                        contentText = "<p class=\'title\'>" + doc.select(".sapo").text() + "</p>";
                    }

                }

            } else {
                contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            }
            String imgThumb = doc.select("figure[class=image] > img").first().attr("src");
            if (!imgThumb.isEmpty()) {
                appModel.setThumb(imgThumb);
            }
            String datePost = doc.select(".tt-capitalize").text();
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Pattern p = Pattern.compile("([0-9]*\\/[0-9]*\\/\\d{4})(\\s-\\s)([0-9]{2}:[0-9]{2})");
            Matcher m = p.matcher(datePost);
            if (m.find()) {
                try {
                    Date createdDate = dt.parse(m.group(1) + " " + m.group(3));
                    appModel.setCreatedDate(createdDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String contentRaw = doc.select("div#divNewsContent > :not(.news-tag)").toString();
            Elements hinhAnh = doc.select("figure[class=image]");
            for (Element el : hinhAnh) {
                String LinkAnh = "<p><img src=\"" + el.select("img").attr("src") + "\" /></p>";
                String motaAnh = "<p><em>" + el.select("figcaption").text() + "</em></p>";
                contentRaw = contentRaw.replace(el.toString(), LinkAnh + "\n" + motaAnh);
            }
//            for (Element cap : chuThich) {
//                contentRaw = contentRaw.replace(cap.toString(), "<em>" + cap.text() + "</em>");
//            }

            contentRaw = contentRaw.replaceAll("<figure*[^\\>]+>(.|\\n)*?<\\/figure>", "");
            contentRaw = contentRaw.replaceAll("<div*[^\\>]+>", "<p>");
            contentRaw = contentRaw.replaceAll("</div>", "</p>");
            contentRaw = contentRaw.replaceAll("<p[^\\>]+>", "<p>");
            contentRaw = contentRaw.replaceAll("<span*[^\\>]+>", "");
            contentRaw = contentRaw.replaceAll("</span>", "");
            contentRaw = contentRaw.replaceAll("<p></p>", "");
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
            String contentaaaaaaaaaa = doc.select("div#divNewsContent > :not(.news-tag)").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }


}
