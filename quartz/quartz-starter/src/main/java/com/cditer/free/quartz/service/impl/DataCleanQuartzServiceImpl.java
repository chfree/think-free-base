package com.cditer.free.quartz.service.impl;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.quartz.logical.model.DataCleanTask;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskView;
import com.cditer.free.quartz.service.IDataCleanQuartzService;
import com.cditer.free.quartz.service.IDataCleanTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataCleanQuartzServiceImpl implements IDataCleanQuartzService {

    @Autowired
    private IDataCleanTaskService dataCleanTaskService;

    @Override
    public void dayEndExec() {
        DataCleanTaskSearch search = new DataCleanTaskSearch();
        List<DataCleanTaskView> dataCleanTaskViews = dataCleanTaskService.queryListViewBySearch(search, PagerModel.asOf(1, 100));

        for (DataCleanTaskView dataCleanTaskView : dataCleanTaskViews) {
            dataCleanTaskService.execCleanTask(dataCleanTaskView);
        }
    }

    @Override
    public void monthEndExec() {

    }

    @Override
    public void yearEndExec() {

    }

    @Override
    public void weekEndExec() {

    }

    @Override
    public void hourEndExec() {

    }
}
