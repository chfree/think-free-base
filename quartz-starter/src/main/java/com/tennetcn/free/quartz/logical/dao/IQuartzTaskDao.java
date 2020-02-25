package com.tennetcn.free.quartz.logical.dao;

import com.tennetcn.free.data.dao.base.ISuperDao;
import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.quartz.logical.viewmodel.QuartzTaskSearch;
import com.tennetcn.free.quartz.logical.model.QuartzTask;
import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:20:25
 * @comment     定时任务表
 */

public interface IQuartzTaskDao extends ISuperDao<QuartzTask>{
    int queryCountBySearch(QuartzTaskSearch search);

    List<QuartzTask> queryListBySearch(QuartzTaskSearch search, PagerModel pagerModel);
}
