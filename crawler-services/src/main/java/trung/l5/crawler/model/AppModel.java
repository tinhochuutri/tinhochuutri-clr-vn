package trung.l5.crawler.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//import trung.l5.crawler.model.enums.Types;

@Document(collection = "tintuc")
@JsonIgnoreProperties({"contentHtml", "author", "contentRaw"})
public class AppModel {

    @SerializedName("id")
    @Expose
    @Id
    public int id;

    @SerializedName("isEn")
    @Expose
    public boolean isEn = false;
    // "title": "Tiêu đề",
    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("source")
    @Expose
    public String source;

    @SerializedName("thumb")
    @Expose
    public String thumb;

    @SerializedName("sourceLink")
    @Expose
    public String sourceLink;

    @SerializedName("linkVideo")
    @Expose
    public String linkVideo;

    @SerializedName("contentRaw")
    @Expose
    public String contentRaw;

    @SerializedName("catid")
    @Expose
    public String catid;

    @SerializedName("contentText")
    @Expose
    public String contentText;

    // "description": "Mô tả",
    @SerializedName("contentHtml")
    @Expose
    public String contentHtml;

    @SerializedName("createdDate")
    @Expose
    public Date createdDate;

    @SerializedName("author")
    @Expose
    public String author;

    @SerializedName("isVideo")
    @Expose
    public Boolean isVideo = false;

    @SerializedName("createdDate")
    @Expose
    public String publishedDate;

    @SerializedName("nextpage")
    @Expose
    public Integer nextpage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIsEn() {
        return isEn;
    }

    public void setIsEn(Boolean isEn) {
        this.isEn = isEn;
    }

    public String getTitle() {
        return title;
    }

    public String getContentRaw() {
        return contentRaw;
    }
    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getAuthor() {
        return author;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getNextpage() {
        return nextpage;
    }

    public void setNextpage(Integer nextpage) {
        this.nextpage = nextpage;
    }

    public String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public int convertMd5ToInt(String md5) {
        try {
            byte[] md5hex = MessageDigest.getInstance("MD5").digest(md5.getBytes());
            BigInteger bigInteger = new BigInteger(bytesToHex(md5hex), 16);
            return bigInteger.intValue();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String calculatorDifferenceTime(Date createdDate) {
        //thời gian chênh lệch
        long difference;
        try {
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR, +7);
            Date oneHourBack = cal.getTime();

            difference = oneHourBack.getTime() - createdDate.getTime();//trả ra số giây difference
            long minutes = difference / 1000 / 60;
            if (minutes < 60) {
                //trả ra phút
                return minutes + " phút trước";
            } else {
                //trả ra giờ
                long hour = minutes / 60;
                if (hour <= 24) {
                    return hour + " giờ trước";
                } else {
                    long days = hour / 24;
                    return days + " ngày trước";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Không xác định";
    }

    public Boolean leftTime(Date createdDate, int day) {
        //thời gian chênh lệch
        long difference;
        try {
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR, +7);
            Date oneHourBack = cal.getTime();

            difference = oneHourBack.getTime() - createdDate.getTime();//trả ra số giây difference
            long dayAgo = difference / 1000 / 60 / 60 / 24;
            if (dayAgo <= day) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
