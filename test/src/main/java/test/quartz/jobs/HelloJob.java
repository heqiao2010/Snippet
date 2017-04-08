package test.quartz.jobs;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job, Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4764919498085510819L;

	/**
	 * Log
	 */
	private Log log = LogFactory.getLog(getClass());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.warn("I'am executing hello job." + new Date());
	}

}
