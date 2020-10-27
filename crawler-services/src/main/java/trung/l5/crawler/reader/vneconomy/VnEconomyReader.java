package trung.l5.crawler.reader.vneconomy;

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
import trung.l5.crawler.schedules.danviet.DanVietCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class VnEconomyReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(DanVietCrawlerItemListJob.class);

    public VnEconomyReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {
        List<String> pages = new ArrayList<>();
        pages.add("http://vneconomy.vn");
        pages.add("http://vneconomy.vn/thoi-su.htm");
        pages.add("http://vneconomy.vn/the-gioi.htm");
//        pages.add("http://vneconomy.vn/van-hoa.htm");
        pages.add("http://vneconomy.vn/thi-truong.htm");
        pages.add("http://vneconomy.vn/cuoc-song-so.htm");
//        pages.add("http://vneconomy.vn/the-thao.html");
//        pages.add("http://vneconomy.vn/giai-tri.htm");
//        pages.add("http://vneconomy.vn/phap-luat.htm");
//        pages.add("http://vneconomy.vn/giao-duc.htm");
//        pages.add("http://vneconomy.vn/y-te.htm");
        pages.add("http://vneconomy.vn/dia-oc.htm");
        pages.add("http://vneconomy.vn/xe-360.htm");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();

        Elements bigNews = doc.select(".top");
        for (Element e : bigNews) {
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://vneconomy.vn" + e.select("a").first().attr("href");
                String title = e.select("h2").first().text();
                String thumb = e.select("a > img").attr("src");
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);

            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
        }
        Elements breakingNewsItemHoriztal = doc.select(".homehlother > ul > li");
        for (Element e : breakingNewsItemHoriztal) {
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://vneconomy.vn" + e.select("a").first().attr("href");
                String title = e.select("h2").first().text();
                String thumb = e.select("a > img").attr("src");

                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                appModel.setThumb(thumb);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception err) {

            }
        }
        Elements newsBreakingItemVetical = doc.select(".homehl1 > ul > li");
        for (Element e : newsBreakingItemVetical) {
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://vneconomy.vn" + e.select("a").first().attr("href");
                String title = e.select("h2").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception err) {
            }
        }

        if (pageUrl == "http://vneconomy.vn") {
            Elements bigNewsOrther = doc.select(".other");
            try {
                Thread.sleep(1000);
                String sourceUrl = "http://vneconomy.vn" + bigNewsOrther.select("h2 > a").first().attr("href");
                String title = bigNewsOrther.select("h2").first().text();
                AppModel appModel = new AppModel();
                appModel.setSourceLink(sourceUrl);
                appModel.setTitle(title);
                setItem(result, appModel, catNews, pageUrl, sourceUrl);
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật trang chủ");
            }
        } else {

            Elements newsBreakingItem = doc.select(".timelinehome > ul > li");
            for (Element e : newsBreakingItem) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = "http://vneconomy.vn" + e.select(".infonews > a").first().attr("href");
                    String title = e.select(".* h3").first().text();
                    String thumb = e.select(".infonews > a > img").attr("src");
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setTitle(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                } catch (Exception err) {
                }
            }
        }
        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: VnEconomy");
        if (pageUrl == "http://vneconomy.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
//        if (pageUrl.contains("http://vneconomy.vn/phap-luat")) {
//            appModel.setCatid(catNews.phap_luat);
//        }
        if (pageUrl.contains("http://vneconomy.vn/the-gioi")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("http://vneconomy.vn/thoi-su")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("http://vneconomy.vn/thi-truong")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("http://vneconomy.vn/cuoc-song-so")) {
            appModel.setCatid(catNews.cong_nghe);
        }
//        if (pageUrl.contains("http://vneconomy.vn/van-hoa")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
//        if (pageUrl.contains("http://vneconomy.vn/giai-tri")) {
//            appModel.setCatid(catNews.giai_tri);
//        }
//        if (pageUrl.contains("http://vneconomy.vn/giao-duc")) {
//            appModel.setCatid(catNews.giao_duc);
//        }
//        if (pageUrl.contains("http://vneconomy.vn/y-te")) {
//            appModel.setCatid(catNews.suc_khoe);
//        }
        if (pageUrl.contains("http://vneconomy.vn/dia-oc")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("http://vneconomy.vn/xe-360")) {
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
            String contentT = "";
            try {
                contentT = doc.select("h2.sapo").first().text();
                appModel.setContentText(contentT);
            } catch (Exception e) {
            }

            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String authorRaw = doc.select(".author > p.name").text();
            String author = "<p><strong>" + authorRaw + "</strong></p>";
//            if (authorRaw.isEmpty()) {
//                authorRaw = doc.select(".detail-auth");
//            }
//            String author = "<p><strong>" + authorRaw.text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select(".author > p.time").text();
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
                contentRaw = doc.select(".contentdetail").toString();
//                if (contentRaw.isEmpty()) {
//                    contentRaw = doc.select(".right_slideshow > .slider_container").toString();
//                }
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");
            contentT = contentT.replaceAll("(\\s{2,1000}|\\n)", "");
            contentRaw = contentRaw.replace(contentT, "");
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
            Boolean isThumBig = false;
            String imgThumb = "";
            String avtDecs = "";
            if (!contentRaw.isEmpty()) {

                try {
                    try {
                        imgThumb = doc.select(".imgdetail > img").first().attr("src");
                        isThumBig = true;
                    } catch (Exception e) {
                        isThumBig = false;
                    }
                    try {
                        avtDecs = "<p><em>" + doc.select(".imgdetail > p[data-role=avatardesc]").first().text() + "</em></p>";
                    } catch (Exception e) {
                    }
                    if (imgThumb.isEmpty()) {
                        imgThumb = doc.select(".VCSortableInPreviewMode[type=photo] > * img").first().attr("data-original");
                        isThumBig = true;
                    }
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                    isThumBig = false;
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
                Elements hinhAnh = doc.select(".VCSortableInPreviewMode[type=photo]");

                if (!hinhAnh.isEmpty()) {
                    for (Element el : hinhAnh) {
                        String LinkAnh = "<p><img src=\"" + el.select("* img").attr("data-original") + "\" /></p>";
                        String motaAnh = "<p><em>" + el.select(".PhotoCMS_Caption").text() + "</em><p>";
                        String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                        contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + motaAnh);
                    }
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
            contentRaw = contentRaw.replaceAll("<p><strong></strong></p>", "");
            String img = isThumBig ? "<p><img src=\"" + imgThumb + "\" /></p>" : "";
            appModel.setContentRaw(contentRaw);
            String contentHtml = "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">\n" +
                    "</head>\n" +

                    content + "\n" + source + "\n" + contentText
                    + "\n<div class='content-detail'>\n" +
                    "  <div>\n" + img + avtDecs + contentRaw + "\n" + author + " </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "</html>";
            appModel.setContentHtml(contentHtml);
        } catch (Exception e) {
            //
        }
    }
}
