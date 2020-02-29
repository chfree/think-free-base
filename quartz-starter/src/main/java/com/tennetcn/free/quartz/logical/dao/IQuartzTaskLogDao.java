package com.tennetcn.free.quartz.logical.dao;

import com.tennetcn.free.data.dao.base.ISuperDao;
import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.quartz.logical.viewmodel.QuartzTaskLogSearch;
import com.tennetcn.free.quartz.logical.model.QuartzTaskLog;
import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 21:56:29
 * @comment     定时任务日志表
 */

public interface IQuartzTaskLogDao extends ISuperDao<QuartzTaskLog>{
    int queryCountBySearch(QuartzTaskLogSearch search);

    List<QuartzTaskLog> queryListBySearch(QuartzTaskLogSearch search, PagerModel pagerModel);
}