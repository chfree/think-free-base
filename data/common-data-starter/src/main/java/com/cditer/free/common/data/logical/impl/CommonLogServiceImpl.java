package com.cditer.free.common.data.logical.impl;

import com.cditer.free.common.data.entity.model.CommonLog;
import com.cditer.free.common.data.entity.viewmodel.CommonLogSearch;
import com.cditer.free.common.data.entity.viewmodel.CommonLogView;
import com.cditer.free.common.data.logical.ICommonLogService;
import com.cditer.free.common.data.mapper.ICommonLogMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.core.message.data.PagerModel;
import java.util.List;


/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-16 12:20:17
 * @comment     通用日志
 */

@Component
public class CommonLogServiceImpl extends SuperService<CommonLog> implements ICommonLogService {
    @Autowired
    ICommonLogMapper commonLogMapper;

    @Override
    public int queryCountBySearch(CommonLogSearch search) {
        return commonLogMapper.queryCountBySearch(search);
    }

    @Override
    public List<CommonLogView> queryListViewBySearch(CommonLogSearch search, PagerModel pagerModel) {
        return commonLogMapper.queryListViewBySearch(search,pagerModel);
    }

    @Override
    public CommonLogView queryModelViewBySearch(CommonLogSearch search) {
        return commonLogMapper.queryModelViewBySearch(search);
    }

    @Override
    public CommonLogView queryModelViewById(String id) {
        CommonLogSearch search = new CommonLogSearch();
        search.setId(id);
        return commonLogMapper.queryModelViewBySearch(search);
    }

    @Override
    public boolean saveCommonLog(CommonLog commonLog) {
        commonLog.autoPkIdAndStatus();

        return applyChange(commonLog);
    }

}
