package trung.l5.crawler.schedules;

import java.io.IOException;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import trung.l5.crawler.schedules.afamily.AFamilyCrawlerItemListJob;
import trung.l5.crawler.schedules.anninhthudo.AnNinhCrawlerItemListJob;
import trung.l5.crawler.schedules.cand.CandCrawlerItemListJob;
import trung.l5.crawler.schedules.congly.CongLyCrawlerItemListJob;
import trung.l5.crawler.schedules.dantri.DanTriCrawlerItemListJob;
import trung.l5.crawler.schedules.danviet.DanVietCrawlerItemListJob;
import trung.l5.crawler.schedules.datviet.DatVietCrawlerItemListJob;
import trung.l5.crawler.schedules.giadinhnetvn.GiaDinhNetVnCrawlerItemListJob;
import trung.l5.crawler.schedules.haitugio.HaiTuGioCrawlerItemListJob;
import trung.l5.crawler.schedules.kienthuc.KienThucCrawlerItemListJob;
import trung.l5.crawler.schedules.laodong.LaoDongCrawlerItemListJob;
import trung.l5.crawler.schedules.nguoilaodong.NLDItemCrawlerItemListJob;
import trung.l5.crawler.schedules.orthers.AutoDeleteJob;
import trung.l5.crawler.schedules.phunuonline.PhuNuOnlineCrawlerItemListJob;
import trung.l5.crawler.schedules.tienphong.TienPhongCrawlerListJob;
import trung.l5.crawler.schedules.ttvn.TTVNCrawlerItemListJob;
import trung.l5.crawler.schedules.tuoitre.TuoiTreCrawlerItemListJob;
import trung.l5.crawler.schedules.vietnamnet.VietNamNetCrawlerItemListJob;
import trung.l5.crawler.schedules.vneconomy.VnEconomyCrawlerItemListJob;
import trung.l5.crawler.schedules.vnexpress.VnExpressCrawlerItemListJob;
import trung.l5.crawler.schedules.vov.VovCrawlerItemListJob;
import trung.l5.crawler.schedules.vtc.VtcCrawlerItemListJob;
import trung.l5.crawler.schedules.videoHaiTuGio.videoHaiTuGioCrawlerItemListJob;

@Configuration
public class SchedulerConfig {

    @Value("${item.list.crawler.cron}")
    String itemListCrawlerCron;
    
    @Value("${auto.delete.cron}")
    String autoDeleteCron;

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//		propertiesFactoryBean.setLocation(new FileSystemResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        SpringJobFactory jobFactory = new SpringJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            JobFactory jobFactory,
            @Qualifier("vnExpressItemListJobTrigger") Trigger vnExpressItemListJobTrigger,
            @Qualifier("tuoiTreItemListJobTrigger") Trigger tuoiTreItemListJobTrigger,
            @Qualifier("danTriItemListJobTrigger") Trigger danTriItemListJobTrigger,
            @Qualifier("laoDongItemListJobTrigger") Trigger laoDongItemListJobTrigger,
            @Qualifier("vietNamNetItemListJobTrigger") Trigger vietNamNetItemListJobTrigger,
            @Qualifier("tienPhongItemListJobTrigger") Trigger tienPhongItemListJobTrigger,
            @Qualifier("vovItemListJobTrigger") Trigger vovItemListJobTrigger,
            @Qualifier("candItemListJobTrigger") Trigger candItemListJobTrigger,
            @Qualifier("congLyListJobTrigger") Trigger congLyListJobTrigger,
            @Qualifier("giaDinhNetVnListJobTrigger") Trigger giaDinhNetVnListJobTrigger,
            @Qualifier("datVietListJobTrigger") Trigger datVietListJobTrigger,
            @Qualifier("danVietListJobTrigger") Trigger danVietListJobTrigger,
            @Qualifier("anNinhListJobTrigger") Trigger anNinhListJobTrigger,
            @Qualifier("HaiTuGioListJobTrigger") Trigger HaiTuGioListJobTrigger,
            @Qualifier("KienThucListJobTrigger") Trigger KienThucListJobTrigger,
            @Qualifier("VnEListJobTrigger") Trigger VnEListJobTrigger,
            @Qualifier("VTCListJobTrigger") Trigger VTCListJobTrigger,
            @Qualifier("PhuNuOnlineListJobTrigger") Trigger PhuNuOnlineListJobTrigger,
            @Qualifier("AFamilyListJobTrigger") Trigger AFamilyListJobTrigger,
            @Qualifier("NLDListJobTrigger") Trigger NLDListJobTrigger,
            @Qualifier("TTVNListJobTrigger") Trigger TTVNListJobTrigger,
            @Qualifier("videoHaiTuGioListJobTrigger") Trigger videoHaiTuGioListJobTrigger,
            @Qualifier("autoDeleteJobTrigger") Trigger autoDeleteJobTrigger

    ) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(
                HaiTuGioListJobTrigger,
                TTVNListJobTrigger,
                tienPhongItemListJobTrigger,
//                vnExpressItemListJobTrigger,
                tuoiTreItemListJobTrigger,
                danTriItemListJobTrigger,
                laoDongItemListJobTrigger,
                videoHaiTuGioListJobTrigger,
                vietNamNetItemListJobTrigger,
                vovItemListJobTrigger,
                candItemListJobTrigger,
                congLyListJobTrigger,
                giaDinhNetVnListJobTrigger,
                datVietListJobTrigger,
                danVietListJobTrigger,
                anNinhListJobTrigger,
                VnEListJobTrigger,
                VTCListJobTrigger,
                PhuNuOnlineListJobTrigger,
                AFamilyListJobTrigger,
                NLDListJobTrigger,
                KienThucListJobTrigger,
                autoDeleteJobTrigger

        );
        factory.setSchedulerName("item-list-scheduler");
        return factory;
    }

    // -------------------------
    // TRIGGERS - LIST
    // -------------------------

    @Bean
    @Qualifier("vnExpressItemListJobTrigger")
    public CronTriggerFactoryBean vnExpressItemListJobTrigger(@Qualifier("vnExpressItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("tuoiTreItemListJobTrigger")
    public CronTriggerFactoryBean tuoiTreItemListJobTrigger(@Qualifier("tuoiTreItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("danTriItemListJobTrigger")
    public CronTriggerFactoryBean danTriItemListJobTrigger(@Qualifier("danTriItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("laoDongItemListJobTrigger")
    public CronTriggerFactoryBean laoDongItemListJobTrigger(@Qualifier("laoDongItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("vietNamNetItemListJobTrigger")
    public CronTriggerFactoryBean vietNamNetItemListJobTrigger(@Qualifier("vietNamNetItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("tienPhongItemListJobTrigger")
    public CronTriggerFactoryBean tienPhongItemListJobTrigger(@Qualifier("tienPhongItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("vovItemListJobTrigger")
    public CronTriggerFactoryBean vovItemListJobTrigger(@Qualifier("vovItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("candItemListJobTrigger")
    public CronTriggerFactoryBean candItemListJobTrigger(@Qualifier("candItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("congLyListJobTrigger")
    public CronTriggerFactoryBean congLyListJobTrigger(@Qualifier("congLyItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("giaDinhNetVnListJobTrigger")
    public CronTriggerFactoryBean giaDinhNetVnListJobTrigger(@Qualifier("giaDinhNetVnItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("datVietListJobTrigger")
    public CronTriggerFactoryBean datVietListJobTrigger(@Qualifier("datVietItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("danVietListJobTrigger")
    public CronTriggerFactoryBean danVietListJobTrigger(@Qualifier("danVietItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("anNinhListJobTrigger")
    public CronTriggerFactoryBean anNinhListJobTrigger(@Qualifier("anNinhItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("VnEListJobTrigger")
    public CronTriggerFactoryBean VnEListJobTrigger(@Qualifier("VnEItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("VTCListJobTrigger")
    public CronTriggerFactoryBean VTCListJobTrigger(@Qualifier("VTCItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("PhuNuOnlineListJobTrigger")
    public CronTriggerFactoryBean PhuNuOnlineListJobTrigger(@Qualifier("PhuNuOnlineItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("KienThucListJobTrigger")
    public CronTriggerFactoryBean KienThucListJobTrigger(@Qualifier("KienThucItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("AFamilyListJobTrigger")
    public CronTriggerFactoryBean AFamilyListJobTrigger(@Qualifier("AFamilyItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("NLDListJobTrigger")
    public CronTriggerFactoryBean NLDListJobTrigger(@Qualifier("NLDItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("TTVNListJobTrigger")
    public CronTriggerFactoryBean TTVNListJobTrigger(@Qualifier("TTVNItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("HaiTuGioListJobTrigger")
    public CronTriggerFactoryBean HaiTuGioListJobTrigger(@Qualifier("HaiTuGioItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }

    @Bean
    @Qualifier("videoHaiTuGioListJobTrigger")
    public CronTriggerFactoryBean videoHaiTuGioListJobTrigger(@Qualifier("videoHaiTuGioItemListJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(itemListCrawlerCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }
//////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    @Qualifier("vnExpressItemListJob")
    public JobDetailFactoryBean vnExpressItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(VnExpressCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("vnexpress-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("tuoiTreItemListJob")
    public JobDetailFactoryBean tuoiTreItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(TuoiTreCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("tuoi-tre-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("danTriItemListJob")
    public JobDetailFactoryBean danTriItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DanTriCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("dan-tri-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("laoDongItemListJob")
    public JobDetailFactoryBean laoDongItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(LaoDongCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("lao-dong-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("vietNamNetItemListJob")
    public JobDetailFactoryBean vietNamNetItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(VietNamNetCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("viet-nam-net-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("tienPhongItemListJob")
    public JobDetailFactoryBean tienPhongItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(TienPhongCrawlerListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("tien-phong-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("vovItemListJob")
    public JobDetailFactoryBean vovItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(VovCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("vov-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("candItemListJob")
    public JobDetailFactoryBean candItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(CandCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("cand-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("congLyItemListJob")
    public JobDetailFactoryBean congLyItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(CongLyCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("cong-ly-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("giaDinhNetVnItemListJob")
    public JobDetailFactoryBean giaDinhNetVnItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(GiaDinhNetVnCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("gia-dinh-net-vn-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("datVietItemListJob")
    public JobDetailFactoryBean datVietItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DatVietCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("dat-viet-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("danVietItemListJob")
    public JobDetailFactoryBean danVietItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DanVietCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("dan-viet-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("anNinhItemListJob")
    public JobDetailFactoryBean anNinhItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AnNinhCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("an-ninh-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("KienThucItemListJob")
    public JobDetailFactoryBean KienThucItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(KienThucCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("KienThuc-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("VnEItemListJob")
    public JobDetailFactoryBean VnEItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(VnEconomyCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("vne-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("VTCItemListJob")
    public JobDetailFactoryBean VTCItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(VtcCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("vtc-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("PhuNuOnlineItemListJob")
    public JobDetailFactoryBean PhuNuOnlineItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PhuNuOnlineCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("phu-nu-online-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("AFamilyItemListJob")
    public JobDetailFactoryBean AFamilyItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AFamilyCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("afamily-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("NLDItemListJob")
    public JobDetailFactoryBean NLDItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(NLDItemCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("nld-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("TTVNItemListJob")
    public JobDetailFactoryBean TTVNItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(TTVNCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("TTVM-item-list-job");
        return factoryBean;
    }

    @Bean
    @Qualifier("HaiTuGioItemListJob")
    public JobDetailFactoryBean HaiTuGioItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(HaiTuGioCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("HaiTuGio-item-list-job");
        return factoryBean;
    }
    @Bean
    @Qualifier("videoHaiTuGioItemListJob")
    public JobDetailFactoryBean videoHaiTuGioItemListJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(videoHaiTuGioCrawlerItemListJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("video-HaiTuGio-item-list-job");
        return factoryBean;
    }
    // ORTHER
    @Bean
    @Qualifier("autoDeleteJobTrigger")
    public CronTriggerFactoryBean autoDeleteJobTrigger(@Qualifier("autoDeleteJob") JobDetail jobDetail) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setCronExpression(autoDeleteCron);
        cron.setJobDetail(jobDetail);
        cron.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY);
        return cron;
    }
    @Bean
    @Qualifier("autoDeleteJob")
    public JobDetailFactoryBean autoDeleteJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AutoDeleteJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("autoDeleteJob");
        return factoryBean;
    }
}