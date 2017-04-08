package test.quartz;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;

public class QuartzTestBean implements InitializingBean, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -412378131784516673L;

	/**
	 * Log
	 */
	private Log log = LogFactory.getLog(getClass());

	public void afterPropertiesSet() throws Exception {
		log.info("Initializing beans...");
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();
			
			scheduler.shutdown();

		} catch (SchedulerException se) {
			log.warn(null, se);
		}
	}
	
	/**
	 * 執行任務
	 */
	public void execute(){
		log.info("I'am executing a task...");
	}
}