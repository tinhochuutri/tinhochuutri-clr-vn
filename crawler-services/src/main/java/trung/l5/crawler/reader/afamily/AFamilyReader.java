package trung.l5.crawler.reader.afamily;

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
import trung.l5.crawler.schedules.afamily.AFamilyCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class AFamilyReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(AFamilyCrawlerItemListJob.class);

    public AFamilyReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("http://afamily.vn");
        pages.add("http://afamily.vn/xa-hoi.chn");
        pages.add("http://afamily.vn/the-gioi.chn");
//        pages.add("http://afamily.vn/van-hoa");
//        pages.add("http://afamily.vn/kinh-te");
//        pages.add("http://afamily.vn/cong-nghe");
//        pages.add("http://afamily.vn/the-thao");
        pages.add("http://afamily.vn/giai-tri.chn");
//        pages.add("http://afamily.vn/phap-luat");
//        pages.add("http://afamily.vn/giao-duc");
        pages.add("http://afamily.vn/suc-khoe.chn");
        pages.add("http://afamily.vn/nha-hay.chn");
//        pages.add("http://afamily.vn/xe360");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "http://afamily.vn") {
            Elements bigNews = doc.select(".afwbtb-left.afwbtb-topnew-content > .afwbtb-info");
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://afamily.vn" + bigNews.select("a").first().attr("href");
                String title = bigNews.select("a").first().attr("title");
//                String thumb = bigNews.select("a > img").attr("src");
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
//                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItemHoriztal = doc.select("ul.afwbo-ul > li");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://afamily.vn" + e.select("* a").first().attr("href");
                    String title = e.select("* a").first().attr("title");
//                    String thumb = e.select("a > img").attr("src");

                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
//                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

//            .spec-news-sitebar >.trending.first
            Elements newsBreakingItemVetical = doc.select("div[data-marked-zoneid=af_home_bs1] > li");
            for (Element e : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://afamily.vn" + e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }
            try {
                Elements itemNews = doc.select("ul.afwblulhrc-ul.swiper-wrapper > li");
                for (Element e : itemNews) {
                    try {
                        Thread.sleep(1000);
                        String sourceUrl = "http://afamily.vn" + e.select("a").first().attr("href");
                        String title = e.select("a").first().attr("title");
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    } catch (Exception err) {

                    }
                }
            } catch (Exception e) {

            }
        } else {

            Elements contentBreaking = doc.select(".afctr-ul > li");
            for (Element e : contentBreaking) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://afamily.vn" + e.select("* a").first().attr("href");
                    String title = e.select("* a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception e2) {
                    LOGGER.info("Không lấy được tin nổi bật chuyên mục");
                }
            }

//            Elements contentBreakingRight = doc.select(".afwbl-ul > li.afwblu-li");
//            for (Element e : contentBreakingRight) {
//                try {
//                    Thread.sleep(1000);
//                    String sourceUrl = "http://afamily.vn" + e.select("* h2 > a").first().attr("href");
//                    String title = e.select("* h2 > a").first().attr("title");
//                    AppModel appModel = new AppModel();
//                    appModel.setSourceLink(sourceUrl);
//                    appModel.setTitle(title);
//                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
//
//                } catch (Exception err) {
//
//                }
//            }
        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: AFamily");
        if (pageUrl == "http://afamily.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
//        if (pageUrl.contains("http://afamily.vn/phap-luat")) {
//            appModel.setCatid(catNews.phap_luat);
//        }
        if (pageUrl.contains("http://afamily.vn/the-gioi.chn")) {
            appModel.setCatid(catNews.the_gioi);
        }
//        if (pageUrl.contains("http://afamily.vn/the-thao")) {
//            appModel.setCatid(catNews.the_thao);
//        }
        if (pageUrl.contains("http://afamily.vn/xa-hoi.chn")) {
            appModel.setCatid(catNews.xa_hoi);
        }
//        if (pageUrl.contains("http://afamily.vn/kinh-te")) {
//            appModel.setCatid(catNews.kinh_te);
//        }
//        if (pageUrl.contains("http://afamily.vn/cong-nghe")) {
//            appModel.setCatid(catNews.cong_nghe);
//        }
//        if (pageUrl.contains("http://afamily.vn/van-hoa")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("http://afamily.vn/giai-tri.chn")) {
            appModel.setCatid(catNews.giai_tri);
        }
//        if (pageUrl.contains("http://afamily.vn/giao-duc")) {
//            appModel.setCatid(catNews.giao_duc);
//        }
        if (pageUrl.contains("http://afamily.vn/suc-khoe.chn")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("http://afamily.vn/nha-hay.chn")) {
            appModel.setCatid(catNews.nha_dat);
        }
//        if (pageUrl.contains("http://afamily.vn/xe360")) {
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
                String contentT = doc.select(".afcbc-sapo").text();
                appModel.setContentText(contentT);
            } catch (Exception e) {
            }

            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String author = "";

            try {
                author = "<p><strong>" + doc.select(".afcba-name,.afcba-source").text() + "</strong></p>";
            } catch (Exception e) {

            }

//            if (authorRaw.isEmpty()) {
//                authorRaw = doc.select(".detail-auth");
//            }
//            String author = "<p><strong>" + authorRaw.text() + "</strong></p>";

            try {
                String datePost = "";

                try {
//                    datePost = doc.select("meta").toString();
                    datePost = doc.select(".afcbat-data").attr("title");

                } catch (Exception e) {

                }

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT-7"));
                Pattern p = Pattern.compile("([0-9]{4})-([0-9]{2})-([0-9]{2})[a-zA-Z]([0-9]{2}:[0-9]{2})");
                Matcher m = p.matcher(datePost);
                if (m.find()) {
                    try {
                        Date createdDate = dt.parse(m.group(3) + "/" + m.group(2) + "/" + m.group(1) + " " + m.group(4));
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
                contentRaw = doc.select(".afcbc-body").toString();
//                if (contentRaw.isEmpty()) {
//                    contentRaw = doc.select(".right_slideshow > .slider_container").toString();
//                }
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
                    imgThumb = doc.select(".VCSortableInPreviewMode[type=Photo] > * img").first().attr("src");
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
//                    try {
//                        if (imgThumb.isEmpty()) {
//                            imgThumb = doc.select(".right_slideshow > .slider_container > .item_slider > * img.lazy").first().attr("src");
//                        }
//                    } catch (Exception er) {
//                    }
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
                Elements hinhAnh = doc.select(".VCSortableInPreviewMode[type=Photo]");

                if (!hinhAnh.isEmpty()) {
                    for (Element el : hinhAnh) {
                        String LinkAnh = "<p><img src=\"" + el.select("* img").attr("src") + "\" /></p>";
                        String moTaAnh = "<p><em>" + el.select(".PhotoCMS_Caption").text() + "</em></p>";
                        String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                        contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + moTaAnh);
                    }

                } else {
//                    hinhAnh = doc.select(".right_slideshow > .slider_container > .item_slider");
//                    if (!hinhAnh.isEmpty()) {
//                        for (Element el : hinhAnh) {
//                            String src = el.select("* img.lazy").attr("src");
//                            if(src.isEmpty()) {
//                                src = el.select("* img.lazy").attr("data-original");
//                            }
//                            String LinkAnh = "<p><img src=\"" + src + "\" /></p>";
//                            String motaAnh = "<p><em>" + el.select(".caption").text() + "</em></p>";
//                            String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
//                            contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n" + motaAnh);
//                        }
//                    }
                }

//                if (!itemUrl.contains("infographic")) {
//                    hinhAnh = doc.select(".cms-body > table.contentimg");
//                } else {
//                    hinhAnh = doc.select(".info-content > table.contentimg");
//                }

            }
            try {
                Elements iframeVideos = doc.select(".VCSortableInPreviewMode[type=VideoStream]");
                for (Element e : iframeVideos) {
                    try {
                        String linkVideo = e.attr("data-vid");
                        if (linkVideo != "" || !linkVideo.isEmpty()) {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo("http://video.mediacdn.vn/" + linkVideo);
                            String capVideo = "<p><em>" + e.select(".VideoCMS_Caption").text() + "</em></p>";
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"http://video.mediacdn.vn/" + linkVideo + "\"> </video>" + capVideo);
                        }
                    } catch (Exception er) {
                        contentRaw = contentRaw.replace(e.toString().replaceAll("(\\s{2,1000}|\\n)", ""), "");
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
            contentRaw = contentRaw.replaceAll("((<a.*?>)|(<table.*?<td.*?>)|(<\\/td?>.*<\\/table>))", "");
            contentRaw = contentRaw.replaceAll("(<figure.*?<figcaption>|<\\/figcaption><\\/figure>)", "");
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
            String contentAAA = doc.select(".afcbc-body").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
