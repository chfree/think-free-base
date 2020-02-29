package com.tennetcn.free.quartz.listener;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.tennetcn.free.core.util.PkIdUtils;
import com.tennetcn.free.quartz.enums.ExecPhaseEnum;
import com.tennetcn.free.quartz.job.commJob.BatchCommonJob;
import com.tennetcn.free.quartz.logical.mapper.IQuartzTaskLogMapper;
import com.tennetcn.free.quartz.logical.model.QuartzTaskLog;
import com.tennetcn.free.quartz.logical.service.IQuartzTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-28 21:39
 * @comment
 */

@Slf4j
public class ThinkJobListener implements JobListener {

    private IQuartzTaskLogService quartzTaskLogService;


    public void setQuartzTaskLogService(IQuartzTaskLogService quartzTaskLogService){
        this.quartzTaskLogService = quartzTaskLogService;
    }

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
        saveJobToBeExecutedLog(jobExecutionContext);
    }

    private void saveJobToBeExecutedLog(JobExecutionContext jobExecutionContext){
        jobExecutionContext.put("execId",IdUtil.randomUUID());
        jobExecutionContext.put("startTime",DateUtil.date());
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
        saveJobWasExecutedLog(jobExecutionContext,e);
    }

    private void saveJobWasExecutedLog(JobExecutionContext jobExecutionContext,JobExecutionException e){
        QuartzTaskLog taskLog = getTaskLog(jobExecutionContext);
        taskLog.setExecPhase(ExecPhaseEnum.JOBWASEXECUTED.getKey());
        taskLog.setExecId(jobExecutionContext.get("execId").toString());
        taskLog.setStartTime((DateTime) jobExecutionContext.get("startTime"));
        taskLog.setEndTime(DateUtil.date());
        long msDiff=DateUtil.betweenMs(taskLog.getEndTime(),taskLog.getStartTime());
        taskLog.setMsDiff(msDiff);

        if(e!=null){
            taskLog.setResult("error");
            taskLog.setErrorMessage(e.getMessage());
        }

        quartzTaskLogService.addModel(taskLog);
    }

    private QuartzTaskLog getTaskLog(JobExecutionContext jobExecutionContext){
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap map =jobDetail.getJobDataMap();


        QuartzTaskLog taskLog = new QuartzTaskLog();
        taskLog.setId(PkIdUtils.getId());
        taskLog.setRecordTime(DateUtil.date());
        taskLog.setResult("success");
        taskLog.setBeanName(map.getString(BatchCommonJob.EXEC_SERVICE));
        taskLog.setMethodName(map.getString(BatchCommonJob.EXEC_METHOD));
        taskLog.setParameter(map.getString(BatchCommonJob.EXEC_PARAMETER));
        taskLog.setTaskName(map.getString(BatchCommonJob.TASK_NAME));

        return taskLog;
    }
}
