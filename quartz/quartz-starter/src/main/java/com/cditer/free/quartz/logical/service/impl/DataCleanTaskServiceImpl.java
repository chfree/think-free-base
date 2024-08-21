package com.cditer.free.quartz.logical.service.impl;

import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.data.dao.base.ISqlExecutor;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.utils.SqlExpressionFactory;
import com.cditer.free.quartz.enums.DataCleanExecType;
import com.cditer.free.quartz.logical.mapper.IDataCleanTaskMapper;
import com.cditer.free.quartz.logical.model.DataCleanTask;
import com.cditer.free.quartz.logical.viewmodel.DataCleanExecResult;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskView;
import com.cditer.free.quartz.service.IDataCleanExecService;
import com.cditer.free.quartz.service.IDataCleanTaskService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.core.message.data.PagerModel;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-12 12:52:26
 * @comment     数据清理任务
 */

@Slf4j
@Component
public class DataCleanTaskServiceImpl extends SuperService<DataCleanTask> implements IDataCleanTaskService {

    @Autowired
    IDataCleanTaskMapper dataCleanTaskMapper;

    @Autowired
    ISqlExecutor sqlExecutor;

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
        if(DataCleanExecType.DATA_TABLE.getValue().equals(dataCleanTask.getExecType())){
            execDataTable(dataCleanTask);
            return;
        }
        if(DataCleanExecType.SHELL.getValue().equals(dataCleanTask.getExecType())){
            execShell(dataCleanTask);
            return;
        }
        if(DataCleanExecType.SERVICE.getValue().equals(dataCleanTask.getExecType())){
            execService(dataCleanTask);
        }
    }

    private void execService(DataCleanTask dataCleanTask) {
        IDataCleanExecService cleanExecService = SpringContextUtils.getCurrentContext().getBean(dataCleanTask.getTableName(), IDataCleanExecService.class);
        DataCleanExecResult dataCleanExecResult = cleanExecService.execDataClean(dataCleanTask);
    }

    private void execShell(DataCleanTask dataCleanTask) {

        List<String> list = new ArrayList<>();
        list.add("sh");
        list.add(dataCleanTask.getTableName());

        String filter = dataCleanTask.getFilter();
        if(StringUtils.hasText(filter)) {
            String[] split = filter.split(",");
            Collections.addAll(list, split);
        }

        String[] path = list.toArray(new String[0]);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process pro = runtime.exec(path);
        }catch (IOException ex){
            log.error("execShell_error", ex);
        }
    }

    private void execDataTable(DataCleanTask dataCleanTask) {
        ISqlExpression delSqlExp = SqlExpressionFactory.createExpression();

        delSqlExp.delete().from(dataCleanTask.getTableName());
        if(StringUtils.hasText(dataCleanTask.getFilter())){
            delSqlExp.andWhere(dataCleanTask.getFilter());
        }
        sqlExecutor.delete(delSqlExp);
    }
}
