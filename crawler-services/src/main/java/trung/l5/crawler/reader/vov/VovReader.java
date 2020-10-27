package trung.l5.crawler.reader.vov;

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
import trung.l5.crawler.schedules.vov.VovCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class VovReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(VovCrawlerItemListJob.class);

    public VovReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();
        pages.add("https://vov.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/xa-hoi/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/phap-luat/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/the-gioi/trang" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://vov.vn/van-hoa-giai-tri/trang" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/kinh-te/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/the-thao/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/van-hoa/nghe-si/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/xa-hoi/giao-duc/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/suc-khoe/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/oto-xe-may/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/kinh-te/dia-oc/trang" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://vov.vn/cong-nghe/trang" + i;
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://vov.vn") {
            Elements bigNews = doc.select(".highlight-1 > .rank-1 > .story");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://vov.vn" + bigNews.select(".story__heading > a").first().attr("href");
                String title = bigNews.select(".story__heading > a").first().text();
                String thumb = bigNews.select("figure.story__thumb > a > img").attr("src");
                String contentText = bigNews.select(".story__desc").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setContentText(contentText);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItem = doc.select(".highlight-1 > .rank-2 > article.story");
            for (Element e : breakingNewsItem) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vov.vn" + e.select(".story__heading > a").first().attr("href");
                    String title = e.select(".story__heading > a").first().text();
                    String thumb = e.select("figure.story__thumb > a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            Elements breakingNewsItemHoriztal = doc.select(".highlight-1 > .rank-3 > article.story");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vov.vn" + e.select(".story__heading > a").first().attr("href");
                    String title = e.select(".story__heading > a").first().text();
                    String thumb = e.select("figure.story__thumb > a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            Elements newsBreakingItemVetical = doc.select(".most-view.panel > .list-stories-1 > article.story");
            for (Element r : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vov.vn" + r.select(".story__heading > a").first().attr("href");
                    String title = r.select(".story__heading > a").first().text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
        } else {

            Elements contentBreaking = doc.select(".highlight-2 >.rank-1 > .stories-style-11 > article.story");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://vov.vn" + contentBreaking.select(".story__heading > a").first().attr("href");
                String title = contentBreaking.select(".story__heading > a").first().text();
                String thumb = contentBreaking.select("figure.story__thumb > a > img").attr("src");
                String contentText = contentBreaking.select(".story__desc").text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                appModel.setContentText(contentText);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentBreakingRight = doc.select(".rank-2 >.wrap > article.story");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vov.vn" + e.select(".story__heading > a").first().attr("href");
                    String title = e.select(".story__heading > a").first().text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }

            Elements contentItemsEle = doc.select(".cate-news > .stories-style-6 > article.story");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://vov.vn" + e.select(".story__heading > a").first().attr("href");
                    String title = e.select(".story__heading > a").first().text();
                    String thumb = e.select("figure.story__thumb > a > img").attr("src");
                    String contentText = e.select(".story__desc").text();
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
        appModel.setSource("Nguồn: VOV");
        if (pageUrl == "https://vov.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://vov.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://vov.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://vov.vn/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://vov.vn/xa-hoi")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://vov.vn/kinh-te")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://vov.vn/cong-nghe")) {
            appModel.setCatid(catNews.cong_nghe);
        }

//        if (pageUrl.contains("https://vov.vn/van-hoa-giai-tri/") || pageUrl.contains("https://vov.vn/video-clip/van-hoa-giai-tri")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("https://vov.vn/van-hoa/nghe-si")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://vov.vn/xa-hoi/giao-duc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://vov.vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://vov.vn/kinh-te/dia-oc")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://vov.vn/oto-xe-may")) {
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
                    String contentT = doc.select(".article__sapo").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {
                }

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String author = "<p><strong>" + doc.select(".cms-author").text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select("time").text();
                } catch (Exception e) {
                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT"));
                Pattern p = Pattern.compile("([0-9]{2}:[0-9]{2})(,\\s*)([0-9]*\\/[0-9]*\\/\\d{4})");
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
                contentRaw  = doc.select(".cms-body").toString();
                if(contentRaw.isEmpty()) {
                    contentRaw  = doc.select(".info-content").toString();
                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

            try {
                String RELATE = doc.select(".boxVideoRelated").toString();
                RELATE = RELATE.replaceAll("(\\s{2,1000}|\\n)", "");
                contentRaw = contentRaw.replace(RELATE, "");
            } catch (Exception e) {}
            try {
                Elements qc = doc.select(".cms-body >* .fyi-full-width");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {

                Elements qc = doc.select(".cms-body >* .position-code");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            if (!contentRaw.isEmpty()) {
                try {
                    String imgThumb = doc.select("img.cms-photo").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                }

                try {
                    Elements relatePost = doc.select("article.cms-relate");
                    for (Element e : relatePost) {
                        String re = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(re, "");
                    }

                } catch (Exception e) {
                }

                try {
                    String coTheThich = doc.select(".relatedHead").toString();
                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(coTheThich, "");
                } catch (Exception e) {}

                try {
                    String ulThich = doc.select(".mainRelated").toString();
                    ulThich = ulThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(ulThich, "");
                } catch (Exception e) {}
                Elements hinhAnh = null;
                if(!itemUrl.contains("infographic")) {
                    hinhAnh = doc.select(".cms-body > table.contentimg");
                } else {
                    hinhAnh = doc.select(".info-content > table.contentimg");
                }
                for (Element el : hinhAnh) {
                    String LinkAnh = "<p><img src=\"" + el.select("tbody > tr > td > img").attr("src") + "\" /></p>";
                    String motaAnh = "<p><em>" + el.select("tbody > tr > td > img").attr("cms-photo-caption") + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                }
            }
            try {
                Elements iframeVideos = doc.select(".cms-body >* script");
                for (Element e : iframeVideos) {
                    Pattern p = Pattern.compile("((https:\\/\\/streaming.vov.vn.*?.(mp4|mov))',)");
                    Matcher m = p.matcher(e.toString());
                    if (m.find()) {
                        try {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo(m.group(2));
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(2) + "\"> </video>");
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
            contentRaw = contentRaw.replaceAll("(<p><\\/p>)", "");
            contentRaw = contentRaw.replaceAll("&amp;", "&");
            contentRaw = contentRaw.replaceAll("(<p><iframe.*?<\\/p>|<script.*?<\\/script>|<br>|<blockquote.*?>|<\\/blockquote>|<hr>)", "");
            contentRaw = contentRaw.replaceAll("<i>", "<em>");
            contentRaw = contentRaw.replaceAll("<\\/i>", "</em>");
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
           String contentAAAAAAAAAAAAAAAA  = doc.select(".cms-body").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
