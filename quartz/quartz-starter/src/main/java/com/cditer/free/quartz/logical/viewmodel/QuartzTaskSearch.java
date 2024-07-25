package com.cditer.free.quartz.logical.viewmodel;

import com.cditer.free.quartz.logical.model.QuartzTask;
import lombok.Data;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:18:16
 * @comment     定时任务表
 */

@Data
public class QuartzTaskSearch extends QuartzTask {
    /**
     * not-id
     */
    private String notId;

    /**
     * 任务名称模糊搜索
     */
    private String likeName;

    /**
     * 方法名称模糊搜索
     */
    private String likeMethodName;
}
