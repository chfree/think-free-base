package com.tennetcn.free.quartz.service;

import com.tennetcn.free.quartz.logical.model.QuartzTask;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-27 12:16
 * @comment
 */

public interface IQuartzService {
    /**
     * 初始化task
     */
    boolean initTask(QuartzTask task);

    /**
     * 根据name初始化task
     */
    boolean initTask(String taskName);

    /**
     * 初始化所有task
     */
    boolean initAllTaks();

    /**
     * 刷新所有task
     */
    boolean refreshAllTask();

    /**
     * 根据name刷新task
     */
    boolean refreshTask(String taskName);


    void registerListener();
}
