import javax.swing.text.Document;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String text = " \n" +

                "<div>\n" +
                "  <img src=\"https://cdn.tuoitre.vn/thumb_w/586/2019/4/22/cam-thai-train-afp-15559274954093656762.jpg\"\n" +
                "    id=\"img_1679fda0-64e6-11e9-acff-e310774680aa\" w=\"800\" h=\"532\"\n" +
                "    alt=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 1.\"\n" +
                "    title=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 1.\" rel=\"lightbox\"\n" +
                "    photoid=\"1679fda0-64e6-11e9-acff-e310774680aa\" type=\"photo\" style=\"max-width:100%;\"\n" +
                "    data-original=\"https://cdn.tuoitre.vn/2019/4/22/cam-thai-train-afp-15559274954093656762.jpg\" width=\"\" height=\"\">\n" +
                "</div>\n" +
                "<div class=\"PhotoCMS_Caption\">\n" +
                "  <p data-placeholder=\"[nhập chú thích]\" class=\"\">Thủ tướng Campuchia Hun Sen (trái) và người đồng cấp Thái Lan Prayut\n" +
                "    Chan-O-Cha trên chuyến xe lửa ngày 22-4 - Ảnh: AFP</p>\n" +
                "</div>\n" +
                "<span style=\"\">Hãng tin AFP cho biết Thủ tướng Campuchia Hun Sen và người đồng cấp Thái Lan Prayut Chan-o-Cha hôm nay\n" +
                "  (22-4) đã chứng kiến lễ ký kết thông đường tàu tại một đồn biên phòng Thái Lan.&nbsp;</span>\n" +
                "<span style=\"\">Hai người sau đó cùng lên đoàn tàu lửa do Thái Lan tặng để đến thị trấn Poipet của Campuchia.</span><br>\n" +
                "Hai nguyên thủ quốc gia của Campuchia và Thái Lan đã bước xuống tàu ở nhà ga Poipet, hai tay giơ cao vẫy chào trong sự\n" +
                "hoan hô nhiệt liệt của đám đông đang chờ đợi, vẫy cờ hai nước.\n" +
                "Ông Hun Sen đã mô tả hành trình của họ là \"lịch sử\" và cảm ơn Thái Lan vì đã nỗ lực \"để kết nối lại tuyến đường sắt giữa\n" +
                "Campuchia và Thái Lan\".\n" +
                "Thủ tướng Hun Sen cũng nói rằng tuyến đường sắt sẽ giúp kết nối tốt hơn giữa đất nước của ông với các láng giềng Đông\n" +
                "Nam Á khác và thúc đẩy kinh tế cũng như thương mại.\n" +
                "<div>\n" +
                "  <img src=\"https://cdn.tuoitre.vn/thumb_w/586/2019/4/22/cam-thai-train-01-fb-15559276188351512059101.jpg\"\n" +
                "    id=\"img_5fec5410-64e6-11e9-a75d-9fc98ba63d68\" w=\"620\" h=\"413\"\n" +
                "    alt=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 2.\"\n" +
                "    title=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 2.\" rel=\"lightbox\"\n" +
                "    photoid=\"5fec5410-64e6-11e9-a75d-9fc98ba63d68\" type=\"photo\"\n" +
                "    data-original=\"https://cdn.tuoitre.vn/2019/4/22/cam-thai-train-01-fb-15559276188351512059101.jpg\" width=\"\"\n" +
                "    height=\"\">\n" +
                "</div>\n" +
                "<div class=\"PhotoCMS_Caption\">\n" +
                "  <p data-placeholder=\"[nhập chú thích]\" class=\"\">Đoàn tàu lửa tại ga Aranyaprathet ở Sa Kaeo, Thái Lan ngày 21-4 để\n" +
                "    chuẩn bị cho hành trình tới Poipet, Campuchia trong ngày thông đường tàu - Ảnh: từ Facebook</p>\n" +
                "</div>\n" +
                "Thương mại song phương giữa Thái Lan và Campuchia hiện đạt mức 6 tỉ USD.\n" +
                "Năm 2018, Campuchia đã mở lại phần đường sắt cuối cùng dài 370km từ thủ đô Phnom Penh đến biên giới Thái Lan.\n" +
                "Ngân hàng Phát triển châu Á đã tài trợ cho việc tái xây dựng tuyến đường này với 13 triệu USD.\n" +
                "Phần lớn tuyến đường sắt Campuchia, xây dựng dưới thời Pháp thuộc, đã bị hư hại trong thời kỳ chiến tranh.\n" +
                "Đoạn đường sắt dài 48km gần thị trấn Poipet đã bị phá hủy năm 1973, trong khi phần còn lại dẫn đến Phnom Penh bị đóng\n" +
                "cửa trong hơn một thập kỷ vì tình trạng kém an toàn.\n" +
                "<h1>Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau</h1>\n" +
                "<div>TTO - Sau 45 năm, một tuyến đường sắt kết nối lại Campuchia và Thái Lan vừa chính thức khánh thành trong nỗ lực cắt\n" +
                "  giảm thời gian di chuyển và thúc đẩy thương mại giữa các nước láng giềng Đông Nam Á.</div>\n" +
                "<div>Nguồn: Tuổi Trẻ</div>\n" +
                "<div>\n" +
                "  <img src=\"https://cdn.tuoitre.vn/thumb_w/586/2019/4/22/cam-thai-train-afp-15559274954093656762.jpg\"\n" +
                "    id=\"img_1679fda0-64e6-11e9-acff-e310774680aa\" w=\"800\" h=\"532\"\n" +
                "    alt=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 1.\"\n" +
                "    title=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 1.\" rel=\"lightbox\"\n" +
                "    photoid=\"1679fda0-64e6-11e9-acff-e310774680aa\" type=\"photo\" style=\"max-width:100%;\"\n" +
                "    data-original=\"https://cdn.tuoitre.vn/2019/4/22/cam-thai-train-afp-15559274954093656762.jpg\" width=\"\" height=\"\">\n" +
                "</div>\n" +
                "<div class=\"PhotoCMS_Caption\">\n" +
                "  <p data-placeholder=\"[nhập chú thích]\" class=\"\">Thủ tướng Campuchia Hun Sen (trái) và người đồng cấp Thái Lan Prayut\n" +
                "    Chan-O-Cha trên chuyến xe lửa ngày 22-4 - Ảnh: AFP</p>\n" +
                "</div>\n" +
                "<span style=\"\">Hãng tin AFP cho biết Thủ tướng Campuchia Hun Sen và người đồng cấp Thái Lan Prayut Chan-o-Cha hôm nay\n" +
                "  (22-4) đã chứng kiến lễ ký kết thông đường tàu tại một đồn biên phòng Thái Lan.&nbsp;</span>\n" +
                "<span style=\"\">Hai người sau đó cùng lên đoàn tàu lửa do Thái Lan tặng để đến thị trấn Poipet của Campuchia.</span><br>\n" +
                "Hai nguyên thủ quốc gia của Campuchia và Thái Lan đã bước xuống tàu ở nhà ga Poipet, hai tay giơ cao vẫy chào trong sự\n" +
                "hoan hô nhiệt liệt của đám đông đang chờ đợi, vẫy cờ hai nước.\n" +
                "Ông Hun Sen đã mô tả hành trình của họ là \"lịch sử\" và cảm ơn Thái Lan vì đã nỗ lực \"để kết nối lại tuyến đường sắt giữa\n" +
                "Campuchia và Thái Lan\".\n" +
                "Thủ tướng Hun Sen cũng nói rằng tuyến đường sắt sẽ giúp kết nối tốt hơn giữa đất nước của ông với các láng giềng Đông\n" +
                "Nam Á khác và thúc đẩy kinh tế cũng như thương mại.\n" +
                "<div>\n" +
                "  <img src=\"https://cdn.tuoitre.vn/thumb_w/586/2019/4/22/cam-thai-train-01-fb-15559276188351512059101.jpg\"\n" +
                "    id=\"img_5fec5410-64e6-11e9-a75d-9fc98ba63d68\" w=\"620\" h=\"413\"\n" +
                "    alt=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 2.\"\n" +
                "    title=\"Hai thủ tướng Campuchia và Thái Lan đi xe lửa cùng nhau - Ảnh 2.\" rel=\"lightbox\"\n" +
                "    photoid=\"5fec5410-64e6-11e9-a75d-9fc98ba63d68\" type=\"photo\"\n" +
                "    data-original=\"https://cdn.tuoitre.vn/2019/4/22/cam-thai-train-01-fb-15559276188351512059101.jpg\" width=\"\"\n" +
                "    height=\"\">\n" +
                "</div>\n" +
                "<div class=\"PhotoCMS_Caption\">\n" +
                "  <p data-placeholder=\"[nhập chú thích]\" class=\"\">Đoàn tàu lửa tại ga Aranyaprathet ở Sa Kaeo, Thái Lan ngày 21-4 để\n" +
                "    chuẩn bị cho hành trình tới Poipet, Campuchia trong ngày thông đường tàu - Ảnh: từ Facebook</p>\n" +
                "</div>\n" +
                "Thương mại song phương giữa Thái Lan và Campuchia hiện đạt mức 6 tỉ USD.\n" +
                "Năm 2018, Campuchia đã mở lại phần đường sắt cuối cùng dài 370km từ thủ đô Phnom Penh đến biên giới Thái Lan.\n" +
                "Ngân hàng Phát triển châu Á đã tài trợ cho việc tái xây dựng tuyến đường này với 13 triệu USD.\n" +
                "Phần lớn tuyến đường sắt Campuchia, xây dựng dưới thời Pháp thuộc, đã bị hư hại trong thời kỳ chiến tranh.\n" +
                "Đoạn đường sắt dài 48km gần thị trấn Poipet đã bị phá hủy năm 1973, trong khi phần còn lại dẫn đến Phnom Penh bị đóng\n" +
                "cửa trong hơn một thập kỷ vì tình trạng kém an toàn.";
        Pattern p = Pattern.compile("<img.*?src=\"(.*?)\"[^\\>]+>");
        Matcher m = p.matcher(text);
        if (m.find()) {
            System.out.println(m.group(1));

//            try {
//
//                System.out.println(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }



    }
}
