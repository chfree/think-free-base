package com.tennetcn.free.quartz.job.commJob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BatchConcurrentJob extends BatchCommonJob {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		super.invoke( context.getJobDetail().getJobDataMap());
	}
}
