package trung.l5.crawler.schedules.videoHaiTuGio;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.reader.videoHaiTuGio.videoHaiTuGioReader;
import trung.l5.crawler.services.AppModelService;

@DisallowConcurrentExecution
public class videoHaiTuGioCrawlerItemListJob implements Job {

    private static final Logger LOGGER = LogManager.getLogger(videoHaiTuGioCrawlerItemListJob.class);

    @Value("${crawl.item.list.interval}")
    long crawlItemListInterval;

    @Value("${vnexpress.start_page}")
    int startPage;
    @Value("${vnexpress.end_page}")
    int endPage;
    @Value("${time.ago.news}")
    int timeAgo;
    @Autowired
    videoHaiTuGioReader videoHaiTuGioReader;

    @Autowired
    AppModelService appModelService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOGGER.info("* Bắt đầu crawler video24h ...");

        //
        List<String> pages = videoHaiTuGioReader.getPages(startPage, endPage);
        for (String pageUrl : pages) {
            try {
                LOGGER.info("* Start crawling item of page " + pageUrl);
                List<AppModel> sourceWrapperModels = videoHaiTuGioReader.readItemsOfPage(pageUrl);
                LOGGER.info("* End crawling item of page " + pageUrl);
//                Apis.saveItemList(sourceWrapperModels);
                List<AppModel> listReverse = new ArrayList<>(sourceWrapperModels);
                List<AppModel> listFinal =  new ArrayList<>();
                Collections.reverse(listReverse);
                AppModel appModel = new AppModel();
                for (AppModel element : listReverse) {
                    if (element.getCreatedDate() != null && element.getIsVideo() && element.getThumb() != null && appModel.leftTime(element.getCreatedDate(), timeAgo)) {
                        listFinal.add(element);
                    }
                }
                appModelService.addAll(listFinal);
                LOGGER.info("* End saving to api " + pageUrl);
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