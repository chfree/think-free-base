package com.tennetcn.free.quartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-28 21:39
 * @comment
 */

@Slf4j
public class ThinkJobListener implements JobListener {
    @Override
    public String getName() {
        return "thinkJobListerner";
    }

    /**
     * Scheduler在JobDetail将要被执行时调用这个方法。
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        log.info("jobToBeExecuted");
    }

    /**
     * Scheduler在JobDetail即将被执行，但又被TriggerListerner否决时会调用该方法
     * vetoJobExecution中返回true时
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        log.info("jobExecutionVetoed");
    }

    /**
     * Scheduler在JobDetail被执行之后调用这个方法
     */
    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        log.info("jobWasExecuted;e is null {}",e==null);
    }
}
