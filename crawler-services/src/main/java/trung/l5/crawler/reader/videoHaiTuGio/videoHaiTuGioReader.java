package trung.l5.crawler.reader.videoHaiTuGio;

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
public class videoHaiTuGioReader implements IReader {
    private static final Logger LOGGER = LogManager.getLogger(HaiTuGioCrawlerItemListJob.class);

    public videoHaiTuGioReader() {
    }

    @Override
    public List<String> getPages(int startPage, int endPage) {

        List<String> pages = new ArrayList<>();
        pages.add("https://www.24h.com.vn/video/clip-nong-cvd808.html");
        pages.add("https://www.24h.com.vn/video/video-tin-tuc-cvd769.html");
        pages.add("https://www.24h.com.vn/video/video-bong-da-the-thao-cvd770.html");
        pages.add("https://www.24h.com.vn/video/video-thoi-trang-cvd771.html");
        pages.add("https://www.24h.com.vn/video/video-giai-tri-cvd772.html");
        pages.add("https://www.24h.com.vn/video/video-cuoi-cvd773.html");
        pages.add("https://www.24h.com.vn/video/video-an-ninh-xa-hoi-cvd774.html");
        pages.add("https://www.24h.com.vn/video/video-am-thuc-du-lich-cvd775.html");
        pages.add("https://www.24h.com.vn/video/video-thoi-trang-hi-tech-cvd776.html");
        return pages;
    }

    @Override
    public List<AppModel> readItemsOfPage(String pageUrl) throws Exception {
        List<AppModel> result = new ArrayList<>();
        TypeCategory catNews = new TypeCategory();

        Document doc = Jsoup.connect(pageUrl).get();
            Elements bigNews = doc.select(".itmVidPl");
            for (Element e : bigNews) {
                    try {

                        Thread.sleep(1000);
                        String sourceUrl = e.select("* .vidBdTit > a").first().attr("href");
                        String title = e.select("* .vidBdTit").first().text();
                        String thumb = e.select(".mnVidImg > a > img").attr("data-original");
                        AppModel appModel = new AppModel();
                        appModel.setSourceLink(sourceUrl);
                        appModel.setTitle(title);
                        appModel.setThumb(thumb);
                        setItem(result, appModel, catNews, pageUrl, sourceUrl);

                    } catch (Exception err) {
                        LOGGER.info("Không lấy được video 24h.com.vn");
                    }
            }

        return result;
    }

    public void setItem(List<AppModel> result, AppModel appModel, TypeCategory catNews, String pageUrl, String sourceUrl) throws Exception {
        appModel.setSource("Nguồn: 24h");
        if (sourceUrl.contains("https://www.24h.com.vn/clip-nong/")) {
            appModel.setCatid(catNews.video_clipnong);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-tin-tuc/")) {
            appModel.setCatid(catNews.video_tintuc);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-bong-da-the-thao/")) {
            appModel.setCatid(catNews.video_bongda);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-thoi-trang")) {
            appModel.setCatid(catNews.video_thoitrang);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-giai-tri/")) {
            appModel.setCatid(catNews.video_giaitri);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-cuoi/")) {
            appModel.setCatid(catNews.video_cuoi);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-an-ninh-xa-hoi/")) {
            appModel.setCatid(catNews.video_anninh);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-am-thuc-du-lich/")) {
            appModel.setCatid(catNews.video_dulich);
        }

        if (sourceUrl.contains("https://www.24h.com.vn/video-thoi-trang-hi-tech/")) {
            appModel.setCatid(catNews.video_congnghe);
        }


        appModel.setId(appModel.convertMd5ToInt(HashUtils.md5(sourceUrl)) > 0
                ? appModel.convertMd5ToInt(HashUtils.md5(sourceUrl))
                : Math.abs(appModel.convertMd5ToInt(HashUtils.md5(sourceUrl))));
        readItem(appModel, sourceUrl);
        result.add(appModel);
    }

    @Override
    public void readItem(AppModel appModel, String itemUrl) throws Exception {
//        itemUrl = "https://www.24h.com.vn/bong-da/truc-tiep-bong-da-eibar-barcelona-messi-van-khat-ban-thang-vi-giay-vang-c48a1051837.htmls";
        Document doc = Jsoup.connect(itemUrl).get();

        try {
            try {
                String contentT = doc.select(".bdVidThp").text();
                appModel.setContentText(contentT);
            } catch (Exception e) {
            }
            String content = "<h3 class=\"title\">" + appModel.getTitle() + "</h3>";
            String source = "<p>" + appModel.getSource() + "</p>";
            String contentText = "<p class=\'title\'>" + appModel.getContentText() + "</p>";
            String author = "<p><strong>" + doc.select(".nguontin").text() + "</strong></p>";

            try {
                String datePost = "";

                try {
                    datePost = doc.select(".bvNw").text();
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
//
//                Elements iframeVideos = doc.select(".nwsHt >* .viewVideoPlay");
                Pattern p = Pattern.compile("(http.*?.(mp4|mov|avi))");
                Matcher m = p.matcher(doc.body().toString());
                if (m.find()) {
                    try {
                        appModel.setIsVideo(true);
                        appModel.setLinkVideo(m.group(0));

                        contentRaw = "<video controls><source src=\"" + m.group(1) + "\"> </video>";
                    } catch (Exception err) {
                    }
                }
            } catch (Exception e) {
            }
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
