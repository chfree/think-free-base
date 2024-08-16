package com.cditer.free.common.data.mapper;


import com.cditer.free.common.data.entity.model.CommonLog;
import com.cditer.free.common.data.entity.viewmodel.CommonLogSearch;
import com.cditer.free.common.data.entity.viewmodel.CommonLogView;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.IMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-16 12:16:36
 * @comment     通用日志
 */

@Mapper
public interface ICommonLogMapper extends IMapper<CommonLog>{

    int queryCountBySearch(@Param("search") CommonLogSearch search);

    List<CommonLogView> queryListViewBySearch(@Param("search") CommonLogSearch search, @Param("pager") PagerModel pagerModel);

    CommonLogView queryModelViewBySearch(@Param("search") CommonLogSearch search);

}
