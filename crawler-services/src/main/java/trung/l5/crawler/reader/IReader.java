package trung.l5.crawler.reader;
import org.jsoup.nodes.Element;
import java.util.List;

import trung.l5.crawler.model.AppModel;

public interface IReader {

    List<String> getPages(int startPage, int endPage);

    List<AppModel> readItemsOfPage(String pageUrl) throws Exception;

    void readItem(AppModel appModel, String itemUrl) throws Exception;
}