package com.tennetcn.free.data.demo.logical.service.impl;

import com.tennetcn.free.data.dao.base.ISqlExpression;
import com.tennetcn.free.data.dao.base.impl.SuperDao;
import com.tennetcn.free.data.demo.logical.mapper.LoginUserMapper;
import com.tennetcn.free.data.demo.logical.model.LoginUser;
import com.tennetcn.free.data.demo.logical.service.ILoginUserService;
import com.tennetcn.free.data.demo.logical.viewmodel.TestUser;
import com.tennetcn.free.data.demo.logical.viewmodel.TestUser1;
import com.tennetcn.free.data.utils.SqlExpressionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginUserServiceImpl extends SuperDao<LoginUser> implements ILoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public List<LoginUser> queryListMPByIds(List<String> ids) {
        return loginUserMapper.queryListMPByIds(ids);
    }

    @Override
    public List<LoginUser> queryTest() {
        ISqlExpression sqlExpression= SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(LoginUser.class);
        return queryList(sqlExpression);
    }

    @Override
    public List<TestUser> queryTestUsers() {
        ISqlExpression sqlExpression= SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(LoginUser.class);
        return queryList(sqlExpression,TestUser.class);
    }

    @Override
    public List<TestUser> queryTestUserxs() {
        ISqlExpression sqlExpression= SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(LoginUser.class).appendSelect("'aaa' as test,'bb' as testName");
        return queryList(sqlExpression,TestUser.class);
    }

    @Override
    public List<TestUser1> queryTestUsers1() {
        ISqlExpression sqlExpression= SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(LoginUser.class);
        return queryList(sqlExpression, TestUser1.class);
    }
}
