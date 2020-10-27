package trung.l5.crawler.reader.phunuonline;

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
import trung.l5.crawler.schedules.phunuonline.PhuNuOnlineCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class PhuNuOnlineReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(PhuNuOnlineCrawlerItemListJob.class);

    public PhuNuOnlineReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("https://www.phunuonline.com.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/thoi-su/?paged=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://www.phunuonline.com.vn/phap-luat/?paged=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/thoi-su/the-gioi/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/van-hoa-giai-tri/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/thi-truong/?paged=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://www.phunuonline.com.vn/the-thao/?paged=" + i;
//            pages.add(currentPage);
//        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://www.phunuonline.com.vn/giai-tri/?paged=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/giao-duc/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/suc-khoe/?paged=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://www.phunuonline.com.vn/the-gioi-phuong-tien/?paged=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.phunuonline.com.vn/nha-dat/?paged=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://www.phunuonline.com.vn/phu-nu-va-cong-nghe/?paged=" + i;
//            pages.add(currentPage);
//        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://www.phunuonline.com.vn") {
            Elements bigNews = doc.select("#magazine-carousel-113 > * .item");
            for (Element e : bigNews) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://www.phunuonline.com.vn" + e.select("a").first().attr("href");
                    String title = e.select("* a > * img").first().attr("alt");
                    String thumb = e.select("* a > * img").attr("src");
                    String contentText = e.select("* .magazine-item-ct").first().text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setContentText(contentText);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception e2) {
                    LOGGER.info("Không lấy được tin nổi bật trang chủ");
                }
            }
            Elements breakingNewsItemHoriztal = doc.select(".magazine-featured-intro > * .magazine-item");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://www.phunuonline.com.vn" + e.select("* a").first().attr("href");
                    String title = e.select("* a").first().attr("title");
                    String thumb = e.select("* a > * img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

            Elements newsBreakingItemVetical = doc.select(".magazine-featured-links >* .magazine-item.link-item");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://www.phunuonline.com.vn" + r.select("* h3 > a").first().attr("href");
                    String title = r.select("* h3 > a").first().text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".t3-content > .col-md-8");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://www.phunuonline.com.vn" + contentBreaking.select("a").first().attr("href");
                String title = contentBreaking.select("a").first().attr("title");
                String thumb = contentBreaking.select("a > img").attr("src");
//                String contentText = contentBreaking.select(".t3-content > .col-md-4").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
//                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentBreakingBottom = doc.select(".row-articles.equal-height > * .magazine-item-media");
            for (Element e : contentBreakingBottom) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://www.phunuonline.com.vn" + e.select(" * a").first().attr("href");
                    String title = e.select(" * a").first().attr("title");
                    String thumb = e.select(" * a > * img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception e2) {
                    // ghi log
                }
            }
            Elements contentItemsEle = doc.select(".items-row > * article");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://www.phunuonline.com.vn" + e.select("* h2.article-title > a").first().attr("href");
                    String title = e.select("* h2.article-title > a").first().attr("title");
                    String thumb = e.select(".article-intro > * img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception e2) {
                    // ghi log
                }
            }
        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: Phụ Nữ Online");
        if (pageUrl == "https://www.phunuonline.com.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/phap-luat/")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/thoi-su/the-gioi/")) {
            appModel.setCatid(catNews.the_gioi);
        }
//        if (pageUrl.contains("https://www.phunuonline.com.vn/the-thao/")) {
//            appModel.setCatid(catNews.the_thao);
//        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/thoi-su/")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/thi-truong/")) {
            appModel.setCatid(catNews.kinh_te);
        }
//        if (pageUrl.contains("https://www.phunuonline.com.vn/phu-nu-va-cong-nghe/")) {
//            appModel.setCatid(catNews.cong_nghe);
//        }

        if (pageUrl.contains("https://www.phunuonline.com.vn/van-hoa-giai-tri/")) {
            appModel.setCatid(catNews.van_hoa);
        }
//        if (pageUrl.contains("https://www.phunuonline.com.vn/giai-tri/")) {
//            appModel.setCatid(catNews.giai_tri);
//        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/giao-duc/")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/suc-khoe/")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://www.phunuonline.com.vn/nha-dat/")) {
            appModel.setCatid(catNews.nha_dat);
        }
//        if (pageUrl.contains("https://www.phunuonline.com.vn/the-gioi-phuong-tien/")) {
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
                    String contentT = doc.select(".article-intro").text();
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
                    datePost = doc.select(".time").text();
//                    if (datePost.isEmpty()) {
//                        datePost = doc.select(".detail-timer").text();
//                    }
                } catch (Exception e) {

                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT"));
                Pattern p = Pattern.compile("([0-9]{2}:[0-9]{2})\\s([0-9]*\\/[0-9]*\\/\\d{4})");
                Matcher m = p.matcher(datePost);
                if (m.find()) {
                    try {
                        String date = m.group(2) + " " + m.group(1);
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
                contentRaw = doc.select("section.article-content").toString();
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
            try {
                Elements qc = doc.select("section.article-content > .tags");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {
                Elements qc = doc.select("section.article-content >* .fb-like");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            if (!contentRaw.isEmpty()) {
                String imgThumb = "";
                try {
                    imgThumb = doc.select("table.tblImage > * img").first().attr("src");
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

                try {
                    Elements relatePost = doc.select(".article-content > .ul_relate");
                    for (Element e : relatePost) {
                        String re = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(re, "");
                    }

                } catch (Exception e) {
                }

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
                    Elements iframeVideos = doc.select("table.tblImage");
                    for (Element e : iframeVideos) {
                        Pattern p = Pattern.compile("(https.*?.(mp4|mov))");
                        Matcher m = p.matcher(e.toString());
                        if (m.find()) {
                            try {
                                appModel.setIsVideo(true);
                                appModel.setLinkVideo(m.group(1));
                                String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                                contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" +  m.group(1) + "\"> </video>");
                            } catch (Exception err) {
                            }
                        }
                    }
                } catch (Exception e) {
                }
                Elements hinhAnh = doc.select("table.tblImage");
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
                    String LinkAnh = "<p><img src=\"" + el.select("* img").attr("src") + "\" /></p>";
                    String motaAnh = "<p><em>" + el.select("* em").text() + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                }
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
            contentRaw = contentRaw.replaceAll("(<section.*?>|<\\/section>|<a.*?href.*?>|<\\/a>)", "");
            contentRaw = contentRaw.replaceAll("(<table.*?<td.*?>|<\\/td>.*?<\\/table>)", "");
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
            String contentRaaaa = doc.select("section.article-content").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
