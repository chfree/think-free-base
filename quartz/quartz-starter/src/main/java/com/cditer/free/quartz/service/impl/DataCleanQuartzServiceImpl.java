package com.cditer.free.quartz.service.impl;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.quartz.enums.DataCleanExecCycle;
import com.cditer.free.quartz.enums.DataCleanExecType;
import com.cditer.free.quartz.enums.DataCleanStatus;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskView;
import com.cditer.free.quartz.logical.service.IDataCleanQuartzService;
import com.cditer.free.quartz.service.IDataCleanTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class DataCleanQuartzServiceImpl implements IDataCleanQuartzService {

    @Autowired
    private IDataCleanTaskService dataCleanTaskService;

    @Override
    public void dayEndExec() {
        execDataCleanTask(DataCleanExecCycle.DAY_END.getValue());
    }

    @Override
    public void monthEndExec() {
        execDataCleanTask(DataCleanExecCycle.MONTH_END.getValue());
    }

    @Override
    public void yearEndExec() {
        execDataCleanTask(DataCleanExecCycle.YEAR_END.getValue());
    }

    @Override
    public void weekEndExec() {
        execDataCleanTask(DataCleanExecCycle.WEEK_END.getValue());
    }

    @Override
    public void hourEndExec() {
        execDataCleanTask(DataCleanExecCycle.HOUR_END.getValue());
    }

    private void execDataCleanTask(String execCycle){
        DataCleanTaskSearch search = new DataCleanTaskSearch();
        search.setStatus(DataCleanStatus.OPEN.getValue());
        search.setExecCycle(execCycle);

        int count = dataCleanTaskService.queryCountBySearch(search);
        int pageSize = 50;
        int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);

        for (int i = 0; i < pageCount; i++) {
            List<DataCleanTaskView> dataCleanTaskViews = dataCleanTaskService.queryListViewBySearch(search, PagerModel.asOf(i, pageSize));
            if(CollectionUtils.isEmpty(dataCleanTaskViews)){
                continue;
            }
            for (DataCleanTaskView dataCleanTaskView : dataCleanTaskViews) {
                dataCleanTaskService.execCleanTask(dataCleanTaskView);
            }
        }
    }
}
