package com.tennetcn.free.quartz.logical.viewmodel;

import java.util.Date;
import lombok.Data;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 21:54:25
 * @comment     定时任务日志表
 */

@Data
public class QuartzTaskLogSearch{
    /**
     * 主键
     */
    private String id;

    /**
     * not-id
     */
    private String notId;

    /**
     * 记录时间
     */
    private Date recordTime;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 对象名称
     */
    private String beanName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 执行阶段
     */
    private String execPhase;

    /**
     * 执行id
     */
    private String execId;

    /**
     * 结果
     */
    private String result;

    /**
     * 异常消息
     */
    private String errorMessage;

}