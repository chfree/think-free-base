package com.cditer.free.quartz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.cditer.free.core.exception.BizException;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.core.util.PkIdUtils;
import com.cditer.free.quartz.exception.QuartzBizException;
import com.cditer.free.quartz.enums.ExecPhaseEnum;
import com.cditer.free.quartz.enums.QuartzTaskConcurrent;
import com.cditer.free.quartz.enums.QuartzTaskStatus;
import com.cditer.free.quartz.job.commJob.BatchCommonJob;
import com.cditer.free.quartz.job.commJob.BatchConcurrentJob;
import com.cditer.free.quartz.job.commJob.BatchDisallowConcurrentJob;
import com.cditer.free.quartz.listener.ThinkJobListener;
import com.cditer.free.quartz.listener.ThinkSchedulerListener;
import com.cditer.free.quartz.listener.ThinkTriggerListener;
import com.cditer.free.quartz.logical.model.QuartzTask;
import com.cditer.free.quartz.logical.model.QuartzTaskLog;
import com.cditer.free.quartz.logical.service.IQuartzTaskLogService;
import com.cditer.free.quartz.logical.service.IQuartzTaskService;
import com.cditer.free.quartz.logical.viewmodel.QuartzTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.TaskExecResult;
import com.cditer.free.quartz.service.IQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-27 12:21
 * @comment
 */

@Slf4j
@Component
public class QuartzServiceImpl implements IQuartzService {

    @Autowired
    IQuartzTaskService quartzTaskService;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    IQuartzTaskLogService quartzTaskLogService;

    @Override
    public boolean initTask(QuartzTask task) {
        if(task==null){
            throw new QuartzBizException("quartz task is null");
        }
        if (!QuartzTaskStatus.OPEN.getValue().equals(task.getStatus())) {
            return false;
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = new JobKey(task.getName(),task.getBeanName());
            TriggerKey triggerKey = new TriggerKey(task.getName(),task.getBeanName());

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron())).build();

            if(scheduler.checkExists(jobKey)) {
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }

            JobDataMap map = initJobDataMap(task);
            Class<? extends BatchCommonJob> jobClass = getJob(task);

            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(task.getDescription())
                    .setJobData(map).storeDurably().build();

            scheduler.scheduleJob(jobDetail, trigger);
        }catch (Exception ex){
            log.error("init quartz task for job fail",ex);
            throw new QuartzBizException("init quartz task for job fail",ex);
        }
        return true;
    }

    private Class<? extends BatchCommonJob>  getJob(QuartzTask task){
        Class<? extends BatchCommonJob> jobClass = null;
        if (QuartzTaskConcurrent.YES.getValue().equals(task.getConcurrent())) {
            jobClass = BatchDisallowConcurrentJob.class;
        } else {
            jobClass = BatchConcurrentJob.class;
        }
        return jobClass;
    }

    @Override
    public boolean stopTask(QuartzTask task) {
        if(task==null){
            throw new QuartzBizException("quartz task is null");
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = new JobKey(task.getName(), task.getBeanName());
            TriggerKey triggerKey = new TriggerKey(task.getName(), task.getBeanName());
            if(scheduler.checkExists(jobKey)){
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }
        }catch (Exception ex){
            log.error("stop quartz task for job fail",ex);
            throw new QuartzBizException("stop quartz task for job fail",ex);
        }
        return true;
    }

    @Override
    public boolean initTask(String taskName) {
        QuartzTask task = quartzTaskService.queryModelByName(taskName);

        return initTask(task);
    }

    @Override
    public boolean stopTask(String taskName) {
        QuartzTask task = quartzTaskService.queryModelByName(taskName);

        return stopTask(task);
    }

    @Override
    public boolean initAllTask() {
        QuartzTaskSearch search = new QuartzTaskSearch();
        search.setStatus(QuartzTaskStatus.OPEN.getValue());
        List<QuartzTask> list = quartzTaskService.queryListBySearch(search,null);

        if (list == null) {
            return true;
        }
        for (QuartzTask task : list) {
            initTask(task);
        }
        return true;
    }

    @Override
    public boolean stopAllTask() {
        return schedulerClear();
    }

    private boolean schedulerClear(){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.clear();
        }catch (Exception ex){
            log.error("stop all quartz task for job fail",ex);
            throw new QuartzBizException("stop all quartz task for job fail",ex);
        }
        return true;
    }

    @Override
    public boolean refreshAllTask() {
        schedulerClear();
        return initAllTask();
    }

    @Override
    public boolean refreshTask(String taskName) {
        return initTask(taskName);
    }

    @Override
    public void registerListener() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            ListenerManager listenerManager = scheduler.getListenerManager();


            ThinkJobListener jobListener = new ThinkJobListener();
            jobListener.setQuartzTaskLogService(quartzTaskLogService);

            listenerManager.addJobListener(jobListener, EverythingMatcher.allJobs());
            listenerManager.addTriggerListener(new ThinkTriggerListener(),EverythingMatcher.allTriggers());
            listenerManager.addSchedulerListener(new ThinkSchedulerListener());
        }catch (Exception ex){
            log.error("注册listener失败",ex);
            throw new QuartzBizException("注册listener失败",ex);
        }
    }

    @Override
    public boolean runTask(String taskName) {
        QuartzTask task = quartzTaskService.queryModelByName(taskName);
        JobDataMap map = initJobDataMap(task);

        QuartzTaskLog taskLog = getQuartzTaskLog(map);
        taskLog.setExecPhase(ExecPhaseEnum.HANDEXEC.getValue());
        taskLog.setExecId(CommonUtils.getTraceId());
        taskLog.setStartTime(DateUtil.date());
        try {
            TaskExecResult invoke = new BatchConcurrentJob().invoke(map);
            if(invoke!=null){
                taskLog.setLogType(invoke.getLogType());
                taskLog.setExecMessage(invoke.getExecMessage());
            }
        }catch (Exception ex){
            taskLog.setResult("error");
            taskLog.setErrorMessage(ex.getMessage());

            throw new BizException("QuartzService runTask is error",ex);
        }finally {
            taskLog.setEndTime(DateUtil.date());
            long msDiff=DateUtil.betweenMs(taskLog.getEndTime(),taskLog.getStartTime());
            taskLog.setMsDiff(msDiff);

            quartzTaskLogService.addModel(taskLog);
        }
        return true;
    }

    private QuartzTaskLog getQuartzTaskLog(JobDataMap map){
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


    private JobDataMap initJobDataMap(QuartzTask entity) {
        JobDataMap map = new JobDataMap();
        map.put(BatchCommonJob.EXEC_SERVICE, entity.getBeanName());
        map.put(BatchCommonJob.EXEC_METHOD, entity.getMethodName());
        map.put(BatchCommonJob.EXEC_PARAMETER, entity.getParameter());
        map.put(BatchCommonJob.TASK_NAME,entity.getName());
        return map;
    }
}
