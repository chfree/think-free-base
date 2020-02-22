package com.tennetcn.free.quartz.job.commJob;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class BatchDisallowConcurrentJob extends BatchCommonJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		super.invoke( context.getJobDetail().getJobDataMap());
	}

}
