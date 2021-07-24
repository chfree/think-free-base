package com.cditer.free.quartz.logical.service.impl;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.quartz.logical.service.IQuartzTaskLogService;
import com.cditer.free.quartz.logical.viewmodel.QuartzTaskLogSearch;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.cditer.free.quartz.logical.model.QuartzTaskLog;
import com.cditer.free.quartz.logical.dao.IQuartzTaskLogDao;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 22:08:51
 * @comment     定时任务日志表
 */

@Component
public class QuartzTaskLogServiceImpl extends SuperService<QuartzTaskLog> implements IQuartzTaskLogService {
    @Autowired
    IQuartzTaskLogDao quartzTaskLogDao;

    @Override
    public int queryCountBySearch(QuartzTaskLogSearch search) {
        return quartzTaskLogDao.queryCountBySearch(search);
    }

    @Override
    public List<QuartzTaskLog> queryListBySearch(QuartzTaskLogSearch search, PagerModel pagerModel) {
        return quartzTaskLogDao.queryListBySearch(search,pagerModel);
    }

}