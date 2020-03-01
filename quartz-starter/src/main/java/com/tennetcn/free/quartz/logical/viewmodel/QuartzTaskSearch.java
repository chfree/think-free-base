package com.tennetcn.free.quartz.logical.viewmodel;

import lombok.Data;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:18:16
 * @comment     定时任务表
 */

@Data
public class QuartzTaskSearch{
    /**
     * 主键
     */
    private String id;

    /**
     * not-id
     */
    private String notId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务名称模糊搜索
     */
    private String likeName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法名称模糊搜索
     */
    private String likeMethodName;

    /**
     * 对象名称
     */
    private String beanName;

    /**
     * 时间表达式
     */
    private String cron;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private String status;

    /**
     * 并发标记
     */
    private String concurrent;

}
