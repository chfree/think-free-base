package com.tennetcn.free.quartz.init;

import com.tennetcn.free.quartz.job.commJob.BatchCommonJob;
import com.tennetcn.free.quartz.job.commJob.BatchConcurrentJob;
import com.tennetcn.free.quartz.job.commJob.BatchDisallowConcurrentJob;
import com.tennetcn.free.quartz.logical.model.QuartzTask;
import com.tennetcn.free.quartz.logical.service.IQuartzTaskService;
import org.quartz.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-26 19:33
 * @comment
 */

@Component
public class QuartzInitializingBean implements InitializingBean {

    @Autowired
    IQuartzTaskService quartzTaskService;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void afterPropertiesSet() throws Exception {
        initTask();
    }

    private void initTask() throws SchedulerException {
        List<QuartzTask> list = quartzTaskService.queryList();
        if (list == null) {
            return;
        }
        for (QuartzTask task : list) {
            initTask(task);
        }
    }

    /**
     * 初台化job
     *
     * @param entity
     * @return
     * @throws SchedulerException
     */
    private boolean initTask(QuartzTask entity) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(entity.getMethodName(), entity.getBeanName());
        TriggerKey triggerKey = new TriggerKey(entity.getMethodName(), entity.getBeanName());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey.getName(), jobKey.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(entity.getCron())).build();
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
        if (!"OPEN".equals(entity.getStatus())) {
            return false;
        }

        JobDataMap map = initJobDataMap(entity);
        Class<? extends BatchCommonJob> jobClass = null;
        if ("1".equals(entity.getConcurrent())) {
            jobClass = BatchDisallowConcurrentJob.class;
        } else {
            jobClass = BatchConcurrentJob.class;
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(entity.getDescription())
                .setJobData(map).storeDurably().build();
        scheduler.scheduleJob(jobDetail, trigger);
        return true;
    }

    private JobDataMap initJobDataMap(QuartzTask entity) {
        JobDataMap map = new JobDataMap();
        map.put(BatchCommonJob.EXEC_SERVICE, entity.getBeanName());
        map.put(BatchCommonJob.EXEC_METHOD, entity.getMethodName());
        map.put(BatchCommonJob.EXEC_PARAMETER_, entity.getParameter());
        return map;
    }
}
