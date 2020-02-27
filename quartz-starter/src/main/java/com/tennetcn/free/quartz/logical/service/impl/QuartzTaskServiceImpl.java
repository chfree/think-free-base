package com.tennetcn.free.quartz.logical.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.tennetcn.free.data.dao.base.impl.SuperService;
import com.tennetcn.free.quartz.logical.model.QuartzTask;
import com.tennetcn.free.quartz.logical.service.IQuartzTaskService;
import com.tennetcn.free.quartz.logical.dao.IQuartzTaskDao;
import com.tennetcn.free.quartz.logical.viewmodel.QuartzTaskSearch;
import com.tennetcn.free.core.message.data.PagerModel;
import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:24:33
 * @comment     定时任务表
 */

@Component
public class QuartzTaskServiceImpl extends SuperService<QuartzTask> implements IQuartzTaskService{
    @Autowired
    IQuartzTaskDao quartzTaskDao;

    @Override
    public int queryCountBySearch(QuartzTaskSearch search) {
        return quartzTaskDao.queryCountBySearch(search);
    }

    @Override
    public List<QuartzTask> queryListBySearch(QuartzTaskSearch search, PagerModel pagerModel) {
        return quartzTaskDao.queryListBySearch(search,pagerModel);
    }

    @Override
    public QuartzTask queryModelByName(String name){
        QuartzTask search = new QuartzTask();
        search.setName(name);

        return quartzTaskDao.queryModel(search);
    }
}
