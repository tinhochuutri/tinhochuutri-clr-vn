package trung.l5.crawler.networks;

import okhttp3.*;
import trung.l5.crawler.model.AppModel;

import java.io.IOException;
import java.util.List;

import static trung.l5.crawler.common.AppConstant.GSON;

public class Apis {

    private static final String HOST = "http://192.168.0.106:8800/producer-api";
//    private static final String HOST = "http://45.77.32.91:8800/producer-api";
//    private static final String HOST = "http://45.77.32.91:8800/producer-api";

    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return "";
    }


    private static String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return "";
    }

    /**
     * Luu chi tiet cua tung tin
     *
     * @param appModel
     * @return
     */
    public static boolean saveItemDetail(AppModel appModel) {
        String url = HOST + "/v1.0/bds/addOrUpdate";
        try {
            System.out.println("Save item detail: " + GSON.toJson(appModel));
            String result = doPostRequest(url, GSON.toJson(appModel));
            System.out.println("Result: " + result);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Luu danh sach cac URL can crawl
     *
     * @param sources
     * @return
     */
    public static boolean saveItemList(List<AppModel> sources) {
        String url = HOST + "/v1.0/bds/addList";
        try {
            String result = doPostRequest(url, GSON.toJson(sources));
            System.out.println("Result: " + result);
            System.out.println("------- Save List ------");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("------- Save List ------");
            return false;
        }
    }

    /**
     * Lay 1 URL can crawl
     *
     * @return
     */
    public static AppModel pop(WebBatDongSan sourceType) throws IOException {
        String url = HOST + "/v1.0/bds/pop?sourceType=" + sourceType.getValue();
        String result = doGetRequest(url);
        return GSON.fromJson(result, AppModel.class);
    }

    /**
     * Lay 1 URL can crawl
     *
     * @return
     */
    public static AppModel notifyItemDeleted(String itemId) throws IOException {
        String url = HOST + "/v1.0/bds/notify/deleted?id=" + itemId;
        String result = doGetRequest(url);
        return GSON.fromJson(result, AppModel.class);
    }

    public enum WebBatDongSan {

        ALO_NHA_DAT("alonhadat"), MUA_BAN("muaban"), BAT_DONG_SAN("batdongsan"), CHO_TOT("chotot");

        private String value;

        WebBatDongSan(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
