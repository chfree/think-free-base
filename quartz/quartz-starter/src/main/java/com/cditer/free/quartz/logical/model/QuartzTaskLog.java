package com.cditer.free.quartz.logical.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

import com.cditer.free.core.message.data.ModelBase;
import lombok.Data;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 21:53:12
 * @comment     定时任务日志表
 */

@Data
@Entity
@Table(name="base_quartz_task_log")
public class QuartzTaskLog extends ModelBase {
    /**
     * 主键
     */
    @Id
    @Column(name="id")
    private String id;

    /**
     * 记录时间
     */
    @Column(name="record_time")
    private Date recordTime;

    /**
     * 任务名称
     */
    @Column(name="task_name")
    private String taskName;

    /**
     * 对象名称
     */
    @Column(name="bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name="method_name")
    private String methodName;

    /**
     * 参数
     */
    @Column(name="parameter")
    private String parameter;

    /**
     * 执行阶段
     */
    @Column(name="exec_phase")
    private String execPhase;

    /**
     * 执行id
     */
    @Column(name="exec_id")
    private String execId;

    /**
     * 开始时间
     */
    @Column(name="start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name="end_time")
    private Date endTime;

    /**
     * 毫秒差
     */
    @Column(name="ms_diff")
    private long msDiff;

    /**
     * 结果
     */
    @Column(name="result")
    private String result;

    /**
     * 异常消息
     */
    @Column(name="error_message")
    private String errorMessage;

}