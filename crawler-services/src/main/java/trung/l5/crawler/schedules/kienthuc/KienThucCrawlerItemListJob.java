package trung.l5.crawler.schedules.kienthuc;


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
import trung.l5.crawler.reader.kienthuc.KienThucReader;
import trung.l5.crawler.services.AppModelService;

@DisallowConcurrentExecution
public class KienThucCrawlerItemListJob implements Job {

    private static final Logger LOGGER = LogManager.getLogger(KienThucCrawlerItemListJob.class);

    @Value("${crawl.item.list.interval}")
    long crawlItemListInterval;

    @Value("${vnexpress.start_page}")
    int startPage;
    @Value("${vnexpress.end_page}")
    int endPage;
    @Value("${time.ago.news}")
    int timeAgo;
    @Autowired
    KienThucReader KienThucReader;

    @Autowired
    AppModelService appModelService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOGGER.info("* Bắt đầu crawler kienthuc ...");

        //
        List<String> pages = KienThucReader.getPages(startPage, endPage);
        for (String pageUrl : pages) {
            try {
                LOGGER.info("* Start crawling item of page " + pageUrl);
                List<AppModel> sourceWrapperModels = KienThucReader.readItemsOfPage(pageUrl);
                LOGGER.info("* End crawling item of page " + pageUrl);
//                Apis.saveItemList(sourceWrapperModels);
                List<AppModel> listReverse = new ArrayList<>(sourceWrapperModels);
                List<AppModel> listFinal =  new ArrayList<>();
                Collections.reverse(listReverse);
                AppModel appModel = new AppModel();
                for (AppModel element : listReverse) {
                    if (element.getCreatedDate() != null && element.getThumb() != null && appModel.leftTime(element.getCreatedDate(), timeAgo)) {
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