package com.cditer.free.data.demo.logical.service.impl;

import com.cditer.free.data.demo.logical.service.ILoginUserService;
import com.cditer.free.data.dao.base.ISqlExecutor;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.demo.logical.mapper.LoginUserMapper;
import com.cditer.free.data.demo.logical.model.LoginUser;
import com.cditer.free.data.demo.logical.viewmodel.TestUser;
import com.cditer.free.data.demo.logical.viewmodel.TestUser1;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginUserServiceImpl extends SuperDao<LoginUser> implements ILoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Autowired
    ISqlExecutor sqlExecutor;

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

    @Override
    public void batchInsert(List<LoginUser> users) {
        batchInsertList(users);
    }

    @Override
    public int querySeq(String seqName) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.addBody("select nextval('"+seqName+"')");

        return sqlExecutor.selectScalarInt(sqlExpression);
    }

    @Override
    public String queryAllParentDeptById(String id) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.callFunction("queryAllDeptParentById").setFunParam("id",id);

        return queryScalar(sqlExpression);
    }

    @Override
    public List<LoginUser> querySelectAll() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(LoginUser.class);

        return queryList(sqlExpression);
    }
}
