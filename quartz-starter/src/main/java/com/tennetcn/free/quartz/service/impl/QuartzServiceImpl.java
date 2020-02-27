package com.tennetcn.free.quartz.service.impl;

import com.tennetcn.free.quartz.enums.QuartzTaskConcurrent;
import com.tennetcn.free.quartz.enums.QuartzTaskStatus;
import com.tennetcn.free.quartz.exception.QuartzBizException;
import com.tennetcn.free.quartz.job.commJob.BatchCommonJob;
import com.tennetcn.free.quartz.job.commJob.BatchConcurrentJob;
import com.tennetcn.free.quartz.job.commJob.BatchDisallowConcurrentJob;
import com.tennetcn.free.quartz.logical.model.QuartzTask;
import com.tennetcn.free.quartz.logical.service.IQuartzTaskService;
import com.tennetcn.free.quartz.service.IQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
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

    @Override
    public boolean initTask(QuartzTask task) {
        if(task==null){
            throw new QuartzBizException("quartz task is null");
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = new JobKey(task.getMethodName(), task.getBeanName());
            TriggerKey triggerKey = new TriggerKey(task.getMethodName(), task.getBeanName());

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey.getName(), jobKey.getGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron())).build();

            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);

            if (!QuartzTaskStatus.OPEN.getKey().equals(task.getStatus())) {
                return false;
            }

            JobDataMap map = initJobDataMap(task);
            Class<? extends BatchCommonJob> jobClass = null;
            if (QuartzTaskConcurrent.YES.getKey().equals(task.getConcurrent())) {
                jobClass = BatchDisallowConcurrentJob.class;
            } else {
                jobClass = BatchConcurrentJob.class;
            }

            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(task.getDescription())
                    .setJobData(map).storeDurably().build();

            scheduler.scheduleJob(jobDetail, trigger);
        }catch (Exception ex){
            log.error("init quartz task for job fail",ex);
            throw new QuartzBizException(ex);
        }
        return true;
    }

    @Override
    public boolean initTask(String taskName) {
        QuartzTask task = quartzTaskService.queryModelByName(taskName);

        return initTask(task);
    }

    @Override
    public boolean initAllTaks() {
        List<QuartzTask> list = quartzTaskService.queryList();
        if (list == null) {
            return true;
        }
        for (QuartzTask task : list) {
            initTask(task);
        }
        return true;
    }

    @Override
    public boolean refreshAllTask() {
        return initAllTaks();
    }

    @Override
    public boolean refreshTask(String taskName) {
        return initTask(taskName);
    }


    private JobDataMap initJobDataMap(QuartzTask entity) {
        JobDataMap map = new JobDataMap();
        map.put(BatchCommonJob.EXEC_SERVICE, entity.getBeanName());
        map.put(BatchCommonJob.EXEC_METHOD, entity.getMethodName());
        map.put(BatchCommonJob.EXEC_PARAMETER_, entity.getParameter());
        return map;
    }
}
