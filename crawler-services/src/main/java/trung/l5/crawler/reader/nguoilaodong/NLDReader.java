package trung.l5.crawler.reader.nguoilaodong;

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
import trung.l5.crawler.schedules.nguoilaodong.NLDItemCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class NLDReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(NLDItemCrawlerItemListJob.class);

    public NLDReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("https://nld.com.vn");
        pages.add("https://nld.com.vn/thoi-su.htm");
        pages.add("https://nld.com.vn/thoi-su-quoc-te.htm");
//        pages.add("https://nld.com.vn/van-hoa.htm");
//        pages.add("https://thitruong.nld.com.vn/tieu-dung.htm");
        pages.add("https://nld.com.vn/cong-nghe.htm");
        pages.add("https://nld.com.vn/the-thao.htm");
        pages.add("https://nld.com.vn/van-nghe.htm");
        pages.add("https://nld.com.vn/phap-luat.htm");
        pages.add("https://nld.com.vn/giao-duc-khoa-hoc.htm");
        pages.add("https://nld.com.vn/suc-khoe.htm");
        pages.add("https://diaoc.nld.com.vn");
//        pages.add("https://nld.com.vn/xe360.htm");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://nld.com.vn") {
            Elements bigNews = doc.select(".homehlr.fl");
            try {
                Thread.sleep(1000);
                String sourceUrl = "https://nld.com.vn" + bigNews.select("a").first().attr("href");
                String title = bigNews.select("h2").first().text();
                String thumb = bigNews.select("* img").attr("src");
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
            Elements breakingNewsItemHoriztal = doc.select(".item-bt-homev4");
            for (Element e : breakingNewsItemHoriztal) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://nld.com.vn" + e.select("* a").first().attr("href");
                    String title = e.select("* h3").first().text();
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
            Elements newsBreakingItemVetical = doc.select("ul#TinTucHome > li");
            for (Element e : newsBreakingItemVetical) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://nld.com.vn" + e.select("h4 > a").first().attr("href");
                    String title = e.select("h4").first().text();
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {

                }
            }

        } else if (pageUrl == "https://diaoc.nld.com.vn") {
            try {
                Elements bigNews = doc.select(".imgnews");
                for (Element e : bigNews) {
                    try {
                        Thread.sleep(1000);
                        String sourceUrl = "https://nld.com.vn" + e.select("a").first().attr("href");
                        String title = e.select("* h4").first().text();
                        String thumb = e.select("a > img").attr("src");

                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        appModel.setThumb(thumb);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    } catch (Exception err) {

                    }
                }

                Elements newHori = doc.select("ul.other_news > li");
                for (Element e : newHori) {
                    try {
                        Thread.sleep(1000);
                        String sourceUrl = "https://nld.com.vn" + e.select("a").first().attr("href");
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
                Elements newVertical = doc.select(".nbright1 > ul > li");
                for (Element e : newVertical) {
                    try {
                        Thread.sleep(1000);
                        String sourceUrl = "https://nld.com.vn" + e.select("a").first().attr("href");
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

                Elements newItemBig = doc.select(".boxtin");
                for (Element e : newItemBig) {
                    try {
                        Elements itemBig = e.select(".border");
                        try {
                            Thread.sleep(1000);
                            String sourceUrl = "https://nld.com.vn" + itemBig.select("a").first().attr("href");
                            String title = itemBig.select(".tin > p").first().text();
                            String thumb = itemBig.select("a > img").attr("src");

                            AppModel appModel = new AppModel();
                            appModel.setSourceLink(sourceUrl);
                            appModel.setTitle(title);
                            appModel.setThumb(thumb);
                            setItem(result, appModel, catNews, pageUrl, sourceUrl);
                        } catch (Exception err) {

                        }
                    } catch (Exception er) {
                    }
                    try {
                        Elements itemSmall = e.select(".border > ul > li");
                        for (Element el : itemSmall) {
                            try {
                                Thread.sleep(1000);
                                String sourceUrl = "https://nld.com.vn" + el.select("a").first().attr("href");
                                String title = el.select("a").first().text();
                                String thumb = el.select("* img").attr("src");
                                AppModel appModel = new AppModel();
                                appModel.setSourceLink(sourceUrl);
                                appModel.setThumb(thumb);
                                appModel.setTitle(title);
                                setItem(result, appModel, catNews, pageUrl, sourceUrl);
                            } catch (Exception err) {

                            }
                        }
                    } catch (Exception er) {
                    }
                }
            } catch (Exception e) {
            }
        } else {

            Elements contentBreaking = doc.select("div[data-boxtype=zonenewsposition]");
            for (Element e : contentBreaking) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://nld.com.vn" + e.select("* a").first().attr("href");
                    String title = e.select("* a").first().attr("title");
                    String thumb = e.select("* img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setThumb(thumb);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception e2) {
                    LOGGER.info("Không lấy được tin nổi bật chuyên mục");
                }
            }

            Elements contentBreakingBot = doc.select(".newsotherv2 > ul > li");
            for (Element e : contentBreakingBot) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://nld.com.vn" + e.select("* a").first().attr("href");
                    String thumb = e.select("* img").attr("src");
                    String title = e.select(("* a")).first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setThumb(thumb);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }
            Elements contentBreakingRight = doc.select("ul.he > li");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://nld.com.vn" + e.select("h4 > a").first().attr("href");
                    String title = e.select("h4 > a").first().attr("title");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }
            Elements contentItem = doc.select(".listitem > .item-bt");
            for (Element e : contentItem) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "https://nld.com.vn" + e.select("a").first().attr("href");
                    String title = e.select("* h3 > a").first().attr("title");
                    String thumb = e.select("a > img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setThumb(thumb);
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);

                } catch (Exception err) {

                }
            }
        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: NLD");
        if (pageUrl == "https://nld.com.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://nld.com.vn/phap-luat")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://nld.com.vn/thoi-su-quoc-te")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://nld.com.vn/the-thao")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://nld.com.vn/thoi-su")) {
            appModel.setCatid(catNews.xa_hoi);
        }
//        if (pageUrl.contains("https://thitruong.nld.com.vn/tieu-dung")) {
//            appModel.setCatid(catNews.kinh_te);
//        }
        if (pageUrl.contains("https://nld.com.vn/cong-nghe")) {
            appModel.setCatid(catNews.cong_nghe);
        }
//        if (pageUrl.contains("https://nld.com.vn/van-hoa")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("https://nld.com.vn/van-nghe")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://nld.com.vn/giao-duc-khoa-hoc")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://nld.com.vn/suc-khoe")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://diaoc.nld.com.vn")) {
            appModel.setCatid(catNews.nha_dat);
        }
//        if (pageUrl.contains("https://nld.com.vn/xe360")) {
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
//        itemUrl = "https://nld.com.vn/du-an/thieu-von-hang-tram-du-an-nha-o-xa-hoi-ach-tac-20190507155739161.htm";
        Document doc = Jsoup.connect(itemUrl).get();

        try {

            try {
                String contentT = doc.select(".sapo").text();
                appModel.setContentText(contentT);
            } catch (Exception e) {
            }
            try {
                String titleP = doc.select("h1").text();
                if (!titleP.isEmpty()) {
                    appModel.setTitle(titleP);
                }
            } catch (Exception e) {
            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
//            String author = "";
//            String authorT = doc.select(".nguon-tin-detail").toString();
//            try {
//                author = "<p><strong>" + doc.select(".nguon-tin-detail").text() + "</strong></p>";
//                if (authorT.isEmpty()) {
//                    author = "<p><strong>" + doc.select(".fr > strong").text() + "</strong></p>";
//                }
//            } catch (Exception e) {
//
//            }

//            if (authorRaw.isEmpty()) {
//                authorRaw = doc.select(".detail-auth");
//            }
//            String author = "<p><strong>" + authorRaw.text() + "</strong></p>";

            try {
                String datePost = "";

                try {
//                    datePost = doc.select("meta").toString();
                    datePost = doc.select(".ngayxuatban").text();

                } catch (Exception e) {
                }
                try {
                    if (datePost.isEmpty()) {
                        datePost = doc.select(".time").text();
                    }
                } catch (Exception e) {

                }
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//                dt.setTimeZone(TimeZone.getTimeZone("GMT-7"));
                Pattern p = Pattern.compile("([0-9]*\\/[0-9]*\\/\\d{4})(\\s)([0-9]{2}:[0-9]{2})");
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
                contentRaw = doc.select(".contentdetail").toString();
                if (contentRaw.isEmpty()) {
                    contentRaw = doc.select(".content-detail").toString();
                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
//            contentRaw = contentRaw.replaceAll(authorT.replaceAll("(\\s{2,1000}|\\n)", ""), "");
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
                try {
                    Elements iframeVideos = doc.select(".VCSortableInPreviewMode[type=VideoStream]");
                    for (Element vid : iframeVideos) {
                        String linkVideo = vid.attr("data-src");
                        Pattern v = Pattern.compile("vid=(.*?.(mp4|mov))");
                        Matcher z = v.matcher(linkVideo);
                        if (z.find()) {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo("http://video.mediacdn.vn/" + z.group(1));
                            String elVideo = vid.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            String capVideo = "<p><em>" + vid.select(".VideoCMS_Caption").text() + "</em></p>";
                            contentRaw = contentRaw.replace(elVideo, "<video controls><source src=\"http://video.mediacdn.vn/" + z.group(1) + "\"> </video>" + capVideo);


                        }
                    }
                } catch (Exception e) {
                }
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

                try {
                    String coTheThich = doc.select("#LoadTinLQRight").toString();
                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(coTheThich, "");
                } catch (Exception e) {
                }
                try {
                    String coTheThich = doc.select(".tlqdetailtotal").toString();
                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(coTheThich, "");
                } catch (Exception e) {
                }
                try {
                    String ulThich = doc.select(".tlqdetail").toString();
                    ulThich = ulThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(ulThich, "");
                } catch (Exception e) {
                }

                try {
                    String coTheThich = doc.select(".tinlienquanold").toString();
                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(coTheThich, "");
                } catch (Exception e) {
                }
                try {
                    String coTheThich = doc.select(".tlqdetailtotal").toString();
                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(coTheThich, "");
                } catch (Exception e) {
                }
                try {
                    String coTheThich = doc.select(".nguon-tin-detail").toString();
                    coTheThich = coTheThich.replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replaceAll(coTheThich, "");
                } catch (Exception e) {
                }

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
                Elements hinhAnhContent = doc.select(".VCSortableInPreviewMode[type=PhotoInContentBox]");
                for (Element el : hinhAnhContent) {
                    String LinkAnh = "<p><img src=\"" + el.select("* img").attr("src") + "\" /></p>";
                    String moTaAnh = "<p><em>" + el.select(".PhotoCMS_Caption").text() + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");


                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + moTaAnh);
                }
//                if (!itemUrl.contains("infographic")) {
//                    hinhAnh = doc.select(".cms-body > table.contentimg");
//                } else {
//                    hinhAnh = doc.select(".info-content > table.contentimg");
//                }

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
            contentRaw = contentRaw.replaceAll("<ul>.*?<\\/ul>", "");
            contentRaw = contentRaw.replace("<i class=\"spriteall tinlienquanrighticon\">", "");
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
            String contentAAA = doc.select(".contentdetail").toString();
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
