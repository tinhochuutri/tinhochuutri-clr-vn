package trung.l5.crawler.reader.haitugio;

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
import trung.l5.crawler.schedules.haitugio.HaiTuGioCrawlerItemListJob;
import trung.l5.crawler.utils.HashUtils;

@Component
public class HaiTuGioReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(HaiTuGioCrawlerItemListJob.class);

    public HaiTuGioReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();
        /// thoi trang
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/thoi-trang-cong-so-c205.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/bi-quyet-mac-dep-c272.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/xu-huong-thoi-trang-c215.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/nguoi-mau-hoa-hau-c214.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/thoi-trang-nam-c260.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/thoi-trang-bon-mua-c238.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/the-gioi-thoi-trang-c672.html?vpage=" + i;
            pages.add(currentPage);
        }

        //the gioi showbiz
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/sao-viet-c757.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/doi-thoai-cung-sao-c730.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/sao-chau-a-c759.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= 8; i++) {
            String currentPage = "https://www.24h.com.vn/sao-hollywood-c760.html?vpage=" + i;
            pages.add(currentPage);
        }

        // bt
        pages.add("https://www.24h.com.vn");
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/tin-tuc-trong-ngay-c46.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/an-ninh-hinh-su-c51.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/tin-tuc-quoc-te-c415.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/giai-tri-c731.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/kinh-doanh-c161.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/the-thao-c101.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/bong-da-c48.html?vpage=" + i;
            pages.add(currentPage);
        }
//        for (int i = startPage; i <= endPage; i++) {
//            String currentPage = "https://www.24h.com.vn/van-hoa/nghe-si.html?vpage=" + i;
//            pages.add(currentPage);
//        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/giao-duc-du-hoc-c216.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/suc-khoe-doi-song-c62.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/o-to-c747.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/xe-may-xe-dap-c748.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/bat-dong-san-c792.html?vpage=" + i;
            pages.add(currentPage);
        }
        for (int i = startPage; i <= endPage; i++) {
            String currentPage = "https://www.24h.com.vn/thoi-trang-hi-tech-c407.html?vpage=" + i;
            pages.add(currentPage);
        }

        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
        if (pageUrl == "https://www.24h.com.vn") {
            Elements bigNews = doc.select(".bxDoC > .bxDoItspc");
            for (Element e : bigNews) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select(".imgFlt.imgNwsHm > a").first().attr("href");
                    String title = e.select("header").first().text();
                    String thumb = e.select(".imgFlt.imgNwsHm > a> img").attr("data-original");
                    if (!sourceUrl.contains("/lich-thi-dau") && !sourceUrl.contains("/ket-qua-thi-dau") && !sourceUrl.contains("/bang-xep-hang") && !sourceUrl.contains("/truc-tiep-bong-da")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        appModel.setThumb(thumb);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    }


                } catch (Exception e2) {
                    LOGGER.info("Không lấy được tin nổi bật trang chủ");
                }
            }
            Elements bigNewsOther = doc.select(".bxDoC > .bxDoIt");
            for (Element e : bigNewsOther) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select(".imgFlt.imgNwsHm > a").first().attr("href");
                    String title = e.select("header").first().text();
                    String thumb = e.select(".imgFlt.imgNwsHm > a> img").attr("data-original");
                    if (!sourceUrl.contains("/lich-thi-dau") && !sourceUrl.contains("/ket-qua-thi-dau") && !sourceUrl.contains("/bang-xep-hang") && !sourceUrl.contains("/truc-tiep-bong-da")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        appModel.setThumb(thumb);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    }

                } catch (Exception e2) {
                    LOGGER.info("Không lấy được tin nổi bật trang chủ");
                }
            }
        } else {

            Elements contentBreaking = doc.select(".col1");
            try {
                Thread.sleep(1000);
                String sourceUrl = contentBreaking.select("* a").first().attr("href");
                String title = contentBreaking.select("* h2").first().text();
                String thumb = contentBreaking.select("* a > img").attr("data-original");
                if (!sourceUrl.contains("/lich-thi-dau") && !sourceUrl.contains("/ket-qua-thi-dau") && !sourceUrl.contains("/bang-xep-hang") && !sourceUrl.contains("/truc-tiep-bong-da")) {
                    AppModel appModel = new AppModel();
                    appModel.setSourceLink(sourceUrl);
                    appModel.setTitle(title);
                    appModel.setThumb(thumb);
                    setItem(result, appModel, catNews, pageUrl, sourceUrl);
                }
            } catch (Exception e2) {
                LOGGER.info("Không lấy được tin nổi bật chuyên mục");
            }

            Elements contentBreakingRight = doc.select(".col2 > article");
            for (Element e : contentBreakingRight) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("* a").first().attr("href");
                    String title = e.select("* a").first().attr("title");
                    String thumb = e.select("* a > img").attr("data-original");
                    if (!sourceUrl.contains("/lich-thi-dau") && !sourceUrl.contains("/ket-qua-thi-dau") && !sourceUrl.contains("/bang-xep-hang") && !sourceUrl.contains("/truc-tiep-bong-da")) {
                        AppModel appModel = new AppModel();
                        appModel.setThumb(thumb);
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);
                    }

                } catch (Exception err) {

                }
            }

            Elements contentItemsEle = doc.select("article.bxDoiSbIt");
            for (Element e : contentItemsEle) {
                try {
                    Thread.sleep(1000);
                    String sourceUrl = e.select("a").first().attr("href");
                    String title = e.select("a").first().attr("title");
                    String thumb = e.select("a > img").attr("data-original");
                    if (!sourceUrl.contains("/lich-thi-dau") && !thumb.isEmpty() && !sourceUrl.contains("/ket-qua-thi-dau") && !sourceUrl.contains("/bang-xep-hang") && !sourceUrl.contains("/truc-tiep-bong-da")) {
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
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
        appModel.setSource("Nguồn: 24h");
        if (pageUrl == "https://www.24h.com.vn") {
            appModel.setCatid(catNews.trang_chu);
        }
        if (pageUrl.contains("https://www.24h.com.vn/an-ninh-hinh-su-c51")) {
            appModel.setCatid(catNews.phap_luat);
        }
        if (pageUrl.contains("https://www.24h.com.vn/tin-tuc-quoc-te-c415")) {
            appModel.setCatid(catNews.the_gioi);
        }
        if (pageUrl.contains("https://www.24h.com.vn/the-thao-c101") || pageUrl.contains("https://www.24h.com.vn/bong-da-c48")) {
            appModel.setCatid(catNews.the_thao);
        }
        if (pageUrl.contains("https://www.24h.com.vn/tin-tuc-trong-ngay-c46")) {
            appModel.setCatid(catNews.xa_hoi);
        }
        if (pageUrl.contains("https://www.24h.com.vn/kinh-doanh-c161")) {
            appModel.setCatid(catNews.kinh_te);
        }
        if (pageUrl.contains("https://www.24h.com.vn/thoi-trang-hi-tech-c407")) {
            appModel.setCatid(catNews.cong_nghe);
        }

//        if (pageUrl.contains("https://www.24h.com.vn/giai-tri-c731/") || pageUrl.contains("https://www.24h.com.vn/video-clip/giai-tri-c731")) {
//            appModel.setCatid(catNews.van_hoa);
//        }
        if (pageUrl.contains("https://www.24h.com.vn/giai-tri-c731")) {
            appModel.setCatid(catNews.giai_tri);
        }
        if (pageUrl.contains("https://www.24h.com.vn/giao-duc-du-hoc-c216")) {
            appModel.setCatid(catNews.giao_duc);
        }
        if (pageUrl.contains("https://www.24h.com.vn/suc-khoe-doi-song-c62")) {
            appModel.setCatid(catNews.suc_khoe);
        }
        if (pageUrl.contains("https://www.24h.com.vn/bat-dong-san-c792")) {
            appModel.setCatid(catNews.nha_dat);
        }
        if (pageUrl.contains("https://www.24h.com.vn/o-to-c747") || pageUrl.contains("https://www.24h.com.vn/xe-may-xe-dap-c748")) {
            appModel.setCatid(catNews.xe_co);
        }

        if (pageUrl.contains("https://www.24h.com.vn/bi-quyet-mac-dep-c272") || pageUrl.contains("https://www.24h.com.vn/thoi-trang-cong-so-c205")) {
            appModel.setCatid(catNews.biquyet_macdep);
        }
        if (pageUrl.contains("https://www.24h.com.vn/xu-huong-thoi-trang-c215")) {
            appModel.setCatid(catNews.xuhuong_thoitrang);
        }
        if (pageUrl.contains("https://www.24h.com.vn/nguoi-mau-hoa-hau-c214")) {
            appModel.setCatid(catNews.nguoimau_hoahau);
        }
        if (pageUrl.contains("https://www.24h.com.vn/thoi-trang-nam-c260")) {
            appModel.setCatid(catNews.thoitrang_nam);
        }
        if (pageUrl.contains("https://www.24h.com.vn/thoi-trang-bon-mua-c238")) {
            appModel.setCatid(catNews.thoitrang_bonmua);
        }
        if (pageUrl.contains("https://www.24h.com.vn/the-gioi-thoi-trang-c672")) {
            appModel.setCatid(catNews.thegioi_thoitrang);
        }
        if (pageUrl.contains("https://www.24h.com.vn/sao-viet-c757")) {
            appModel.setCatid(catNews.sao_viet);
        }
        if (pageUrl.contains("https://www.24h.com.vn/doi-thoai-cung-sao-c730")) {
            appModel.setCatid(catNews.talk_voisao);
        }
        if (pageUrl.contains("https://www.24h.com.vn/sao-chau-a-c759.html")) {
            appModel.setCatid(catNews.sao_chaua);
        }
        if (pageUrl.contains("https://www.24h.com.vn/sao-hollywood-c760.html")) {
            appModel.setCatid(catNews.sao_hollywood);
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
                    String contentT = doc.select(".ctTp").text();
                    appModel.setContentText(contentT);
                } catch (Exception e) {
                }

            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String author = "<p><strong>" + doc.select(".nguontin").text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select(".updTm").text();
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
                contentRaw = doc.select(".nwsHt").toString();
            } catch (Exception e) {
            }
            contentRaw = contentRaw.replaceAll("(\\s{2,1000}|\\n)", "");

            try {
                Elements qc = doc.select("#24h-banner-in-image-close");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {
                Elements qc = doc.select("header");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {

                Elements qc = doc.select(".bmTpSeoBlk");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {

                Elements qc = doc.select(".ctTp");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {

                Elements qc = doc.select(".sbNws");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {

                Elements qc = doc.select(".updTm");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }
            try {

                Elements qc = doc.select(".bv-lq");
                for (Element e : qc) {
                    String el = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(el, "");
                }
            } catch (Exception e) {
            }

            if (!contentRaw.isEmpty()) {
                try {
                    String imgThumb = doc.select(".nwsHt >* img.news-image").first().attr("data-original");
                    if (imgThumb.isEmpty()) {
                        imgThumb = doc.select(".nwsHt >* img.news-image").first().attr("src");
                    }
                    if (!imgThumb.isEmpty()) {
                        appModel.setThumb(imgThumb);
                    }
                } catch (Exception e) {
                }

                Elements hinhAnh = null;
                hinhAnh = doc.select(".nwsHt >* img.news-image");

                for (Element el : hinhAnh) {
                    String img = el.attr("data-original");
                    if (img.isEmpty()) {
                        img = el.attr("src");
                    }
                    String LinkAnh = "<p><img src=\"" + img + "\" /></p>";
//                    String motaAnh = "<p><em>" + el.select("tbody > tr > td > img").attr("cms-photo-caption") + "</em></p>";
                    String elHinhAnh = el.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                    contentRaw = contentRaw.replace(elHinhAnh, LinkAnh + "\n");
                }

            }
            try {

                Pattern p = Pattern.compile("(<p style=\"color:#0000ff;font-style:italic;text-align:center;\">(.*?)<\\/p>)");
                Matcher m = p.matcher(contentRaw);
                while (m.find()) {
                    try {
                        contentRaw = contentRaw.replaceAll(m.group(1).replaceAll("(\\s{2,1000}|\\n)", ""), "<p><em>" + m.group(2) + "</em></p>");

                    } catch (Exception e) {
                    }
                }
            } catch (Exception er) {


            }

            try {
                Elements iframeVideos = doc.select(".nwsHt >* .viewVideoPlay");
                for (Element e : iframeVideos) {
                    Pattern p = Pattern.compile("(http.*?.(mp4|mov|avi))");
                    Matcher m = p.matcher(doc.body().toString());
                    if (m.find()) {
                        try {
                            appModel.setIsVideo(true);
                            appModel.setLinkVideo(m.group(0));
                            String eAtferValidate = e.toString().replaceAll("(\\s{2,1000}|\\n)", "");
                            contentRaw = contentRaw.replace(eAtferValidate, "<video controls><source src=\"" + m.group(0) + "\"> </video>");
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
            contentRaw = contentRaw.replaceAll("(<article.*?>|</article>|<!--.*?-->)", "");
            contentRaw = contentRaw.replaceAll("((<a.*?>)|(<table.*?<td.*?>)|(<\\/td?>.*<\\/table>))", "");
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
