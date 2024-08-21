package com.cditer.free.quartz.service;

import com.cditer.free.quartz.logical.model.DataCleanTask;
import com.cditer.free.quartz.logical.viewmodel.DataCleanExecResult;

public interface IDataCleanExecService {
    /**
     * 自定义执行dataClean任务，并返回结果
     */
    DataCleanExecResult execDataClean(DataCleanTask dataCleanTask);
}
