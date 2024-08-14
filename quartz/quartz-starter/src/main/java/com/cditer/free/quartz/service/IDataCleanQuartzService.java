package com.cditer.free.quartz.service;

public interface IDataCleanQuartzService {
    /**
     * 日终执行
     */
    void dayEndExec();

    /**
     * 月底执行
     */
    void monthEndExec();

    /**
     * 年终执行
     */
    void yearEndExec();

    /**
     * 周执行
     */
    void weekEndExec();

    /**
     * 小时执行
     */
    void hourEndExec();
}
