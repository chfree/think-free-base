package com.cditer.free.quartz.service;

import com.cditer.free.data.dao.base.ISuperService;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.quartz.logical.model.DataCleanTask;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskView;

import java.util.List;


/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-12 12:51:20
 * @comment     数据清理任务
 */

public interface IDataCleanTaskService extends ISuperService<DataCleanTask>{

    /**
     * 根据搜索条件查询[数据清理任务]条数
     * @param search 搜索条件
     * @return [数据清理任务]条数
     */
    int queryCountBySearch(DataCleanTaskSearch search);

    /**
     * 根据搜索条件分页查询[数据清理任务]对象集合
     * @param search 搜索条件
     * @param pagerModel 分页条件
     * @return [数据清理任务]对象集合
     */
    List<DataCleanTaskView> queryListViewBySearch(DataCleanTaskSearch search, PagerModel pagerModel);

    /**
     * 根据搜索条件查询[数据清理任务]对象
     * @param search 搜索条件
     * @return [数据清理任务]对象
     */
    DataCleanTaskView queryModelViewBySearch(DataCleanTaskSearch search);

    /**
     * 根据id查询[数据清理任务]对象
     * @param id 主键id
     * @return [数据清理任务]对象
     */
    DataCleanTaskView queryModelViewById(String id);

    /**
     * 保存一个[数据清理任务]对象
     * @param dataCleanTask [数据清理任务]对象
     * @return 是否成功
     */
    boolean saveDataCleanTask(DataCleanTask dataCleanTask);

}
