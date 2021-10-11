package com.cditer.free.quartz.logical.viewmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
     * 记录时间-开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordTimeStart;

    /**
     * 记录时间-结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordTimeEnd;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务名称模糊搜索
     */
    private String likeTaskName;

    /**
     * 对象名称
     */
    private String beanName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法名称-模糊搜索
     */
    private String likeMethodName;

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

    /**
     * 毫秒差-最小值
     */
    private Long msDiffMin;

    /**
     * 毫秒差-最大值
     */
    private Long msDiffMax;
}