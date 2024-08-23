package com.cditer.free.common.data.logical.impl;

import cn.hutool.core.date.DateUtil;
import com.cditer.free.common.data.entity.model.CommonLog;
import com.cditer.free.common.data.entity.viewmodel.CommonLogSearch;
import com.cditer.free.common.data.entity.viewmodel.CommonLogView;
import com.cditer.free.common.data.enums.CommonLogType;
import com.cditer.free.common.data.logical.ICommonLogService;
import com.cditer.free.common.data.mapper.ICommonLogMapper;
import com.cditer.free.core.inceptor.ILoginModelQuery;
import com.cditer.free.core.message.security.LoginModel;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.core.util.PkIdUtils;
import com.cditer.free.core.util.SpringContextUtils;
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
    private ICommonLogMapper commonLogMapper;

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

    @Override
    public void setLoginModel(CommonLog commonLog) {
        if(commonLog==null){
            return;
        }
        ILoginModelQuery loginModelQuery = SpringContextUtils.getBean(ILoginModelQuery.class);
        if(loginModelQuery==null){
            return;
        }
        LoginModel currentLogin = loginModelQuery.getCurrentLogin();
        if(currentLogin==null){
            return;
        }
        commonLog.setUserId(currentLogin.getId());
        commonLog.setRoleId(currentLogin.getRoleId());
        commonLog.setDeptId(currentLogin.getDeptId());
    }

    private CommonLog buildLog(String logType,String bsnType, String bsnId, String shortMessage){
        CommonLog commonLog = new CommonLog();
        commonLog.setId(PkIdUtils.getId());
        commonLog.setLogType(logType);
        commonLog.setBsnId(bsnId);
        commonLog.setBsnType(bsnType);
        commonLog.setShortMessage(shortMessage);

        commonLog.setRecordStartTime(DateUtil.date());

        // 设置一下用户登录信息
        setLoginModel(commonLog);

        return commonLog;
    }

    @Override
    public void info(String bsnType, String bsnId, String shortMessage) {
        CommonLog commonLog = buildLog(CommonLogType.INFO.getValue(), bsnType, bsnId, shortMessage);

        addModel(commonLog);
    }

    @Override
    public void warn(String bsnType, String bsnId, String shortMessage) {
        CommonLog commonLog = buildLog(CommonLogType.WARN.getValue(), bsnType, bsnId, shortMessage);

        addModel(commonLog);
    }

    @Override
    public void debug(String bsnType, String bsnId, String shortMessage) {
        CommonLog commonLog = buildLog(CommonLogType.DEBUG.getValue(), bsnType, bsnId, shortMessage);

        addModel(commonLog);
    }

    @Override
    public void error(String bsnType, String bsnId, String shortMessage) {
        CommonLog commonLog = buildLog(CommonLogType.ERROR.getValue(), bsnType, bsnId, shortMessage);

        addModel(commonLog);
    }

}
