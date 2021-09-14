package com.cditer.free.quartz.logical.service;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.ISuperService;
import com.cditer.free.quartz.logical.model.QuartzTask;
import com.cditer.free.quartz.logical.viewmodel.QuartzTaskSearch;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:23:28
 * @comment     定时任务表
 */

public interface IQuartzTaskService extends ISuperService<QuartzTask> {
    int queryCountBySearch(QuartzTaskSearch search);

    List<QuartzTask> queryListBySearch(QuartzTaskSearch search, PagerModel pagerModel);

    QuartzTask queryModelByName(String name);
}