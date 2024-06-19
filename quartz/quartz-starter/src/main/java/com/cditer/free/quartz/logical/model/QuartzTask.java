package com.cditer.free.quartz.logical.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

import com.cditer.free.core.message.data.ModelBase;
import lombok.Data;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:12:04
 * @comment     定时任务表
 */

@Data
@Entity
@Table(name="base_quartz_task")
public class QuartzTask extends ModelBase {
    /**
     * 主键
     */
    @Id
    @Column(name="id")
    private String id;

    /**
     * 任务名称
     */
    @Column(name="name")
    private String name;

    /**
     * 方法名称
     */
    @Column(name="method_name")
    private String methodName;

    /**
     * 对象名称
     */
    @Column(name="bean_name")
    private String beanName;

    /**
     * 时间表达式
     */
    @Column(name="cron")
    private String cron;

    /**
     * 参数
     */
    @Column(name="parameter")
    private String parameter;

    /**
     * 描述
     */
    @Column(name="description")
    private String description;

    /**
     * 状态
     */
    @Column(name="status")
    private String status;

    /**
     * 并发标记
     */
    @Column(name="concurrent")
    private String concurrent;

    /**
     * 清理日志时长
     * 小时
     */
    @Column(name="clear_log_time")
    private int clearLogTime;

}