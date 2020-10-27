
package trung.l5.crawler.schedules.orthers;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import trung.l5.crawler.services.AppModelService;

@DisallowConcurrentExecution
public class AutoDeleteJob implements Job {
	private static final Logger LOGGER = LogManager.getLogger(AutoDeleteJob.class);
	@Autowired
	AppModelService appModelService;

	@Value("${auto.delete.before.num.date}")
	int numDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if (numDate == 0) {
			LOGGER.info("Lưu ý khai báo lại auto.delete.before.num.date > 0");
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, numDate * -1);
		appModelService.deleteByCreatedDateBefore(cal.getTime());

	}
}
