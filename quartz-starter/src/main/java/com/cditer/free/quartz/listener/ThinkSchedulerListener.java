package com.cditer.free.quartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-28 22:56
 * @comment
 */

@Slf4j
public class ThinkSchedulerListener implements SchedulerListener {
    /**
     * 部署JobDetail时调用
     */
    @Override
    public void jobScheduled(Trigger trigger) {
        log.info("jobScheduled");
    }

    /**
     * 卸载JobDetail时调用
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        log.info("jobUnscheduled");
    }

    /**
     * 当一个 Trigger 来到了再也不会触发的状态时调用这个方法。除非这个 Job 已设置成了持久性，否则它就会从 Scheduler 中移除。
     */
    @Override
    public void triggerFinalized(Trigger trigger) {
        log.info("triggerFinalized");
    }

    /**
     * Scheduler 调用这个方法是发生在一个 Trigger 被暂停时。
     */
    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        log.info("triggerPaused");
    }

    /**
     * Scheduler 调用这个方法是发生在一个 Trigger 组被暂停时。
     */
    @Override
    public void triggersPaused(String s) {
        log.info("triggersPaused");
    }

    /**
     * Scheduler 调用这个方法是发生成一个 Trigger 从暂停中恢复时。
     */
    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        log.info("triggerResumed");
    }

    /**
     * Scheduler 调用这个方法是发生成一个 Trigger 组从暂停中恢复时。
     */
    @Override
    public void triggersResumed(String s) {
        log.info("triggersResumed");
    }

    /**
     * Scheduler 中添加一个job时
     */
    @Override
    public void jobAdded(JobDetail jobDetail) {
        log.info("jobAdded");
    }

    /**
     * Scheduler 中删除一个job时
     */
    @Override
    public void jobDeleted(JobKey jobKey) {
        log.info("jobDeleted");
    }

    /**
     * Scheduler 中暂停一个job时
     */
    @Override
    public void jobPaused(JobKey jobKey) {
        log.info("jobPaused");
    }

    /**
     * Scheduler 中暂停一组job时
     */
    @Override
    public void jobsPaused(String s) {
        log.info("jobsPaused");
    }

    /**
     * Scheduler 中恢复一个job时
     */
    @Override
    public void jobResumed(JobKey jobKey) {
        log.info("jobResumed");
    }

    /**
     * Scheduler 中恢复一组job时
     */
    @Override
    public void jobsResumed(String s) {
        log.info("jobsResumed");
    }

    /**
     * 在 Scheduler 的正常运行期间产生一个严重错误时调用这个方法。
     */
    @Override
    public void schedulerError(String s, SchedulerException e) {
        log.info("schedulerError");
    }

    /**
     * 当Scheduler处于StandBy模式时，调用该方法
     */
    @Override
    public void schedulerInStandbyMode() {
        log.info("schedulerInStandbyMode");
    }

    /**
     * 当Scheduler 开启时，调用该方法
     */
    @Override
    public void schedulerStarted() {
        log.info("schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
        log.info("schedulerStarting");
    }

    @Override
    public void schedulerShutdown() {
        log.info("schedulerShutdown");
    }

    @Override
    public void schedulerShuttingdown() {
        log.info("schedulerShuttingdown");
    }

    /**
     * 当Scheduler中的数据被清除时，调用该方法。
     */
    @Override
    public void schedulingDataCleared() {
        log.info("schedulingDataCleared");
    }
}
