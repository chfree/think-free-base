package com.cditer.free.quartz.service.impl;

import com.cditer.free.quartz.logical.mapper.IDataCleanTaskMapper;
import com.cditer.free.quartz.logical.model.DataCleanTask;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskView;
import com.cditer.free.quartz.service.IDataCleanTaskService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.core.message.data.PagerModel;
import java.util.List;


/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-12 12:52:26
 * @comment     数据清理任务
 */

@Component
public class DataCleanTaskServiceImpl extends SuperService<DataCleanTask> implements IDataCleanTaskService {
    @Autowired
    IDataCleanTaskMapper dataCleanTaskMapper;

    @Override
    public int queryCountBySearch(DataCleanTaskSearch search) {
        return dataCleanTaskMapper.queryCountBySearch(search);
    }

    @Override
    public List<DataCleanTaskView> queryListViewBySearch(DataCleanTaskSearch search, PagerModel pagerModel) {
        return dataCleanTaskMapper.queryListViewBySearch(search,pagerModel);
    }

    @Override
    public DataCleanTaskView queryModelViewBySearch(DataCleanTaskSearch search) {
        return dataCleanTaskMapper.queryModelViewBySearch(search);
    }

    @Override
    public DataCleanTaskView queryModelViewById(String id) {
        DataCleanTaskSearch search = new DataCleanTaskSearch();
        search.setId(id);
        return dataCleanTaskMapper.queryModelViewBySearch(search);
    }

    @Override
    public boolean saveDataCleanTask(DataCleanTask dataCleanTask) {
        dataCleanTask.autoPkIdAndStatus();

        return applyChange(dataCleanTask);
    }

    @Override
    public void execCleanTask(DataCleanTask dataCleanTask) {

    }

}
