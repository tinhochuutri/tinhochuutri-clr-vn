package trung.l5.crawler.schedules.haitugio;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.reader.haitugio.HaiTuGioReader;
import trung.l5.crawler.services.AppModelService;

@DisallowConcurrentExecution
public class HaiTuGioCrawlerItemListJob implements Job {

    private static final Logger LOGGER = LogManager.getLogger(HaiTuGioCrawlerItemListJob.class);

    @Value("${crawl.item.list.interval}")
    long crawlItemListInterval;

    @Value("${vnexpress.start_page}")
    int startPage;
    @Value("${vnexpress.end_page}")
    int endPage;
    @Value("${time.ago.news}")
    int timeAgo;
    @Autowired
    HaiTuGioReader HaiTuGioReader;

    @Autowired
    AppModelService appModelService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOGGER.info("* Bắt đầu crawler 24h.com.vn ...");

        //
        List<String> pages = HaiTuGioReader.getPages(startPage, endPage);
        for (String pageUrl : pages) {
            try {
                LOGGER.info("* Start crawling item of page " + pageUrl);
                List<AppModel> sourceWrapperModels = HaiTuGioReader.readItemsOfPage(pageUrl);
                LOGGER.info("* End crawling item of page " + pageUrl);
//                Apis.saveItemList(sourceWrapperModels);
                List<AppModel> listReverse = new ArrayList<>(sourceWrapperModels);
                List<AppModel> listFinal =  new ArrayList<>();
                Collections.reverse(listReverse);
                AppModel appModel = new AppModel();
                for (AppModel element : listReverse) {
                    if (element.getCreatedDate() != null && element.getContentRaw() != "" && element.getThumb() != null) {
                        listFinal.add(element);
                    }
                }
                appModelService.addAll(listFinal);
                LOGGER.info("* HOÀN THÀNH LẤY DỮ LIỆU " + pageUrl);
            } catch (Exception e) {
                LOGGER.error("Error has occurred during crawling item list", e);
            }

            try {
                Thread.sleep(crawlItemListInterval);
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(crawlItemListInterval);
                } catch (InterruptedException e1) {
                }
            }
        }
    }
}