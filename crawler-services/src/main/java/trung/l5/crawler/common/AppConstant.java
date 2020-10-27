package trung.l5.crawler.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppConstant {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
}
