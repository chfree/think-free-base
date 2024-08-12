package com.cditer.free.quartz.logical.mapper;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.IMapper;
import com.cditer.free.quartz.logical.model.DataCleanTask;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskSearch;
import com.cditer.free.quartz.logical.viewmodel.DataCleanTaskView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDataCleanTaskMapper extends IMapper<DataCleanTask> {
    int queryCountBySearch(@Param("search") DataCleanTaskSearch search);

    List<DataCleanTaskView> queryListViewBySearch(@Param("search") DataCleanTaskSearch search, @Param("pager") PagerModel pagerModel);

    DataCleanTaskView queryModelViewBySearch(@Param("search") DataCleanTaskSearch search);

}
