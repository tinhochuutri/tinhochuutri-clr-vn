package trung.l5.crawler.controllers;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import trung.l5.crawler.helper.ResponseHelper;
import trung.l5.crawler.model.AppModel;
import trung.l5.crawler.model.AppResponse;
import trung.l5.crawler.services.AppModelService;

@RestController
public class ReaderController {

    private static final Logger LOGGER = LogManager.getLogger(ReaderController.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    AppModelService appModelService;

    @RequestMapping("/health-check")
    @ResponseBody
    public AppResponse healthCheck() {
        return ResponseHelper.makeSuccess("OK");
    }

    AppModel model = new AppModel();

    @RequestMapping(value = "/api/get_arc_by_catid", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
    @ResponseBody
    public Object getArcByCatid(String catid, int page) {
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        LOGGER.info("TRIGGER TIN-TUC CRAWLER");
        try {
            List<AppModel> apps = appModelService.findByCatIdAndIsEn(catid, page, false);
            for (AppModel appModel : apps) {
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.HOUR, +7);
//                Date oneHourBack = cal.getTime();

                appModel.setSource(appModel.getSource() + " - " + model.calculatorDifferenceTime(appModel.getCreatedDate()));
                appModel.setPublishedDate(simpleDateFormat.format(appModel.getCreatedDate()));
                if (apps.size() == 50) {
                    appModel.setNextpage(page + 1);
                } else {
                    appModel.setNextpage(page);
                }
            }
            return apps;
        } catch (Exception e) {
            LOGGER.error("Error has occurred during trigger job", e);
            return ResponseHelper.makeFailure("Failure");
        }
    }

    @RequestMapping(value = "/api/get_video_arc_by_catid", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
    @ResponseBody
    public Object getNewsByIsVideo(String catid, int page) {
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        LOGGER.info("TRIGGER TIN-TUC CRAWLER");
        try {
            List<AppModel> apps = appModelService.findByCatIdAndIsVideoAndIsEn(catid, page, true, false);
            for (AppModel appModel : apps) {
                appModel.setSource(appModel.getSource() + " - " + model.calculatorDifferenceTime(appModel.getCreatedDate()));
                appModel.setPublishedDate(simpleDateFormat.format(appModel.getCreatedDate()));
                if (apps.size() == 50) {
                    appModel.setNextpage(page + 1);
                } else {
                    appModel.setNextpage(page);
                }
            }
            return apps;
        } catch (Exception e) {
            LOGGER.error("Error has occurred during trigger job", e);
            return ResponseHelper.makeFailure("Failure");
        }
    }

    @RequestMapping(value = "/api/get_noi_dung_tin_new", method = RequestMethod.GET, headers = "Accept=text/html", produces = "text/html")
    @ResponseBody
    public Object getNoiDungTinew(int arcid) {
        LOGGER.info("TRIGGER TIN-TUC CRAWLER");
        try {
            AppModel appModel = appModelService.getById(arcid);
            return appModel.getContentHtml();
        } catch (Exception e) {
            LOGGER.error("Error has occurred during trigger job", e);
            return ResponseHelper.makeFailure("Failure");
        }
    }

    @RequestMapping(value = "/api/en/get_arc_by_catid", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
    @ResponseBody
    public Object getEnNewsArcByCatid(String catid, int page) {
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        LOGGER.info("TRIGGER TIN-TUC CRAWLER");
        try {
            List<AppModel> apps = appModelService.findByCatIdAndIsEn(catid, page, true);
            for (AppModel appModel : apps) {
                appModel.setSource(appModel.getSource() + " - " + model.calculatorDifferenceTime(appModel.getCreatedDate()));
                appModel.setPublishedDate(simpleDateFormat.format(appModel.getCreatedDate()));
                if (apps.size() == 50) {
                    appModel.setNextpage(page + 1);
                } else {
                    appModel.setNextpage(page);
                }
            }
            return apps;
        } catch (Exception e) {
            LOGGER.error("Error has occurred during trigger job", e);
            return ResponseHelper.makeFailure("Failure");
        }
    }

    @RequestMapping(value = "/api/en/get_video_arc_by_catid", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
    @ResponseBody
    public Object getEnNewsByIsVideo(String catid, int page) {
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        LOGGER.info("TRIGGER TIN-TUC CRAWLER");
        try {
            List<AppModel> apps = appModelService.findByCatIdAndIsVideoAndIsEn(catid, page, true, true);
            for (AppModel appModel : apps) {
                appModel.setSource(appModel.getSource() + " - " + model.calculatorDifferenceTime(appModel.getCreatedDate()));
                appModel.setPublishedDate(simpleDateFormat.format(appModel.getCreatedDate()));
                if (apps.size() == 50) {
                    appModel.setNextpage(page + 1);
                } else {
                    appModel.setNextpage(page);
                }
            }
            return apps;
        } catch (Exception e) {
            LOGGER.error("Error has occurred during trigger job", e);
            return ResponseHelper.makeFailure("Failure");
        }
    }

    @RequestMapping(value = "/api/en/get_noi_dung_tin_new", method = RequestMethod.GET, headers = "Accept=text/html", produces = "text/html")
    @ResponseBody
    public Object getNoiDungTinNewEn(int arcid) {
        LOGGER.info("TRIGGER TIN-TUC CRAWLER");
        try {
            AppModel appModel = appModelService.getById(arcid);
            return appModel.getContentHtml();
        } catch (Exception e) {
            LOGGER.error("Error has occurred during trigger job", e);
            return ResponseHelper.makeFailure("Failure");
        }
    }
}
