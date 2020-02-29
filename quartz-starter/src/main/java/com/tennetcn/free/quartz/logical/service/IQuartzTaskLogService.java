package com.tennetcn.free.quartz.logical.service;

import com.tennetcn.free.data.dao.base.ISuperService;
import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.quartz.logical.viewmodel.QuartzTaskLogSearch;
import com.tennetcn.free.quartz.logical.model.QuartzTaskLog;
import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 21:58:18
 * @comment     定时任务日志表
 */

public interface IQuartzTaskLogService extends ISuperService<QuartzTaskLog>{
    int queryCountBySearch(QuartzTaskLogSearch search);

    List<QuartzTaskLog> queryListBySearch(QuartzTaskLogSearch search, PagerModel pagerModel);
}