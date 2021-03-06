package trung.l5.crawler.reader.datviet;

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
import trung.l5.crawler.schedules.datviet.DatVietCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class DatVietReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(DatVietCrawlerItemListJob.class);

    public DatVietReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("http://baodatviet.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/chinh-tri-xa-hoi/tin-tuc-thoi-su/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/phap-luat/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/the-gioi/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/van-hoa/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/kinh-te/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/the-thao/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/van-hoa/tin-tuc-giai-tri/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/chinh-tri-xa-hoi/giao-duc/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/doi-song/suc-khoe/?paged=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "http://baodatviet.vn/the-gioi-phuong-tien/?paged=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/bat-dong-san/?paged=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "http://baodatviet.vn/khoa-hoc/cong-nghe/?paged=" + i;
            pages.add(currentPage);
        }


        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();

        Elements bigNews = doc.select(".top_story");
        try {
            Thread.sleep(1000);
            String sourceUrl = "http://baodatviet.vn" + bigNews.select("p.title > a").first().attr("href");
            String title = bigNews.select("p.title > a").text();
            String thumb = bigNews.select("a > img").attr("src");
            AppModel appModel = new AppModel();
            appModel.setSourceLink(sourceUrl);
            appModel.setTitle(title);
            appModel.setThumb(thumb);
            setItem(result, appModel, catNews, pageUrl, sourceUrl);

        } catch (Exception e2) {
            LOGGER.info("Không lấy được tin nổi bật trang chủ");
        }
        Elements breakingNewsItemHoriztal = doc.select("ul.list_top > li");
        for (Element e : breakingNewsItemHoriztal) {
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://baodatviet.vn" + e.select("p.title > a").first().attr("href");
                String title = e.select("p.title > a").text();
                String thumb = e.select("a > img").attr("src");

                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception err) {

            }
        }

//            .spec-news-sitebar >.trending.first
//        Elements newsBreakingItemVetical = doc.select("#moinhat  >* ul.list_200 > li");
//        for (Element e : newsBreakingItemVetical) {
//            try {
//                Thread.sleep(1000);
//                String sourceUrl = "http://baodatviet.vn" + e.select("p.title > a").first().attr("href");
//                String title = e.select("p.title > a").text();
//                AppModel appModel = new AppModel();
//                appModel.setSourceLink(sourceUrl);
//                appModel.setTitle(title);
//                setItem(result, appModel, catNews, pageUrl, sourceUrl);
//            } catch (Exception err) {
//
//            }
//        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: Đất Việt");
        if (pageUrl == "http://baodatviet.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("http://baodatviet.vn/phap-luat/")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("http://baodatviet.vn/the-gioi/")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("http://baodatviet.vn/the-thao/")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("http://baodatviet.vn/chinh-tri-xa-hoi/tin-tuc-thoi-su/")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("http://baodatviet.vn/kinh-te/")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("http://baodatviet.vn/khoa-hoc/cong-nghe/")) {
            appModel.setCatid(catNews.cong_nghe);
        }

        if (pageUrl.contains("http://baodatviet.vn/van-hoa/")) {
            appModel.setCatid(catNews.van_hoa);
        }
        if (pageUrl.contains("http://baodatviet.vn/van-hoa/tin-tuc-giai-tri/")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("http://baodatviet.vn/chinh-tri-xa-hoi/giao-duc/")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("http://baodatviet.vn/doi-song/suc-khoe/")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("http://baodatviet.vn/bat-dong-san/")) {
            appModel.setCatid(catNews.nha_dat);
        }
//        if (pageUrl.contains("http://baodatviet.vn/the-gioi-phuong-tien/")) {
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

            try {
                String contentT = doc.select("h2.lead").text();
                appModel.setContentText(contentT);
            } catch (Exception e) {
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
            try {
                contentRaw = doc.select("#divContent").toString();
                if (contentRaw.isEmpty()) {
                    contentRaw = doc.select(".right_slideshow > .slider_container").toString();
                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

//            String bottomNews = doc.select("h4.subbottomnew").toString().replaceAll("(\\s{2,1000}|\\n)", "");
//            String releasted = doc.select(".VCSortableInPreviewMode[type=RelatedOneNews]").toString().replaceAll("(\\s{2,1000}|\\n)", "");
//            ;
//            contentRaw = contentRaw.replaceAll(bottomNews, "");
//            contentRaw = contentRaw.replaceAll(releasted, "");

//            try {
//                Elements qc = doc.select(".box-widget.post-content > ul.contref");
//                if (qc.isEmpty()) {
//                    qc = doc.select(".detail-content > ul.contref");
//                }
//                for (Element e : qc) {
//                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
//                    contentRaw = contentRaw.replace(el, "");
//                }
//            } catch (Exception e) {
//            }
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
                    imgThumb = doc.select("#divContent > * img").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                    try {
                        if (imgThumb.isEmpty()) {
                            imgThumb = doc.select(".right_slideshow > .slider_container > .item_slider > * img.lazy").first().attr("src");
                        }
                    } catch (Exception er) {
                    }
                }

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
                Elements hinhAnh = doc.select("#divContent > table.tblImage");

                if (!hinhAnh.isEmpty()) {
                    for (Element el : hinhAnh) {
                        String LinkAnh = "<p><img src=\"" + el.select("* img").attr("src") + "\" /></p>";
                        String motaAnh = "<p><em>" + el.select("* .Image").text() + "</em></p>";
                        String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                        contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                    }

                } else {
                    hinhAnh = doc.select(".right_slideshow > .slider_container > .item_slider");
                    if (!hinhAnh.isEmpty()) {
                        for (Element el : hinhAnh) {
                            String src = el.select("* img.lazy").attr("src");
                            if(src.isEmpty()) {
                                src = el.select("* img.lazy").attr("data-original");
                            }
                            String LinkAnh = "<p><img src=\"" + src + "\" /></p>";
                            String motaAnh = "<p><em>" + el.select(".caption").text() + "</em></p>";
                            String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
                        }
                    }
                }

//                if (!itemUrl.contains("infographic")) {
//                    hinhAnh = doc.select(".cms-body > table.contentimg");
//                } else {
//                    hinhAnh = doc.select(".info-content > table.contentimg");
//                }

            }
            try {
                Elements iframeVideos = doc.select("#divContent > script");
                for (Element e : iframeVideos) {
                    Pattern p = Pattern.compile("'(http:\\/\\/st.*?.(mp4|mov))");
                    Matcher m = p.matcher(e.toString());
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
