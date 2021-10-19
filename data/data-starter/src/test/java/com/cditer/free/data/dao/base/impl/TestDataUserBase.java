package com.cditer.free.data.dao.base.impl;

import cn.hutool.core.date.DateUtil;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.data.BaseTest;
import com.cditer.free.data.dao.base.ISqlExecutor;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.test.mapper.ITestDataUserMapper;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class TestDataUserBase  extends BaseTest {

    @Autowired
    private ITestDataUserMapper testDataUserMapper;

    @Autowired
    ISqlExecutor sqlExecutor;

    @Before
    public void initTestData() {
        List<TestDataUser> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TestDataUser testDataUser = new TestDataUser();
            testDataUser.setId("TEST_" + CommonUtils.getShortUUID());
            testDataUser.setName("test" + i);
            testDataUser.setPassword("000000");
            testDataUser.setAccount("cheng");
            testDataUser.setCreateDate(DateUtil.date());

            list.add(testDataUser);
        }


        testDataUserMapper.insertListEx(list);
    }

    @After
    public void destroyTestData() {
        ISqlExpression delSql = SqlExpressionFactory.createExpression();
        delSql.delete().from(TestDataUser.class).andRightLikeNoEmpty(TestDataUser::getId, "TEST_");


        sqlExecutor.delete(delSql);
    }
}
