package com.cditer.free.quartz.logical.dao;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.ISuperDao;
import com.cditer.free.quartz.logical.model.QuartzTaskLog;
import com.cditer.free.quartz.logical.viewmodel.QuartzTaskLogSearch;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 21:56:29
 * @comment     定时任务日志表
 */

public interface IQuartzTaskLogDao extends ISuperDao<QuartzTaskLog> {
    int queryCountBySearch(QuartzTaskLogSearch search);

    List<QuartzTaskLog> queryListBySearch(QuartzTaskLogSearch search, PagerModel pagerModel);
}