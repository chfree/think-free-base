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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SqlExecutorImplTest extends BaseTest {

    @Autowired
    ISqlExecutor sqlExecutor;

    @Autowired
    ITestDataUserMapper testDataUserMapper;

    @Before
    public void initTestData(){
        List<TestDataUser> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TestDataUser testDataUser = new TestDataUser();
            testDataUser.setId("TEST_"+ CommonUtils.getShortUUID());
            testDataUser.setName("test"+i);
            testDataUser.setPassword("000000");
            testDataUser.setAccount("cheng");
            testDataUser.setCreateDate(DateUtil.date());

            list.add(testDataUser);
        }


        testDataUserMapper.insertListEx(list);
    }

    @After
    public void destroyTestData(){
        ISqlExpression delSql = SqlExpressionFactory.createExpression();
        delSql.delete().from(TestDataUser.class).andRightLikeNoEmpty(TestDataUser::getId, "TEST_");

        sqlExecutor.delete(delSql);
    }

    @Test
    public void update() {
        List<TestDataUser> list = testDataUserMapper.select(null);
        TestDataUser testDataUser = list.get(0);

        ISqlExpression updateSql = SqlExpressionFactory.createExpression();
        updateSql.update(TestDataUser.class).setColumn(TestDataUser::getAccount, "CH").andEq("id", testDataUser.getId());

        sqlExecutor.update(updateSql);

        TestDataUser testDataUserOfDb = testDataUserMapper.selectByPrimaryKey(testDataUser.getId());

        Assert.assertEquals(testDataUserOfDb.getAccount(), "CH");

    }

    @Test
    public void delete() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void queryScalar() {
    }

    @Test
    public void queryScalarInt() {
    }

    @Test
    public void selectList() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testUpdate1() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testDelete1() {
    }

    @Test
    public void testInsert() {
    }

    @Test
    public void testInsert1() {
    }

    @Test
    public void testSelectList() {
    }

    @Test
    public void testSelectList1() {
    }

    @Test
    public void testSelectList2() {
    }

    @Test
    public void selectListEx() {
    }

    @Test
    public void testSelectList3() {
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void testSelectOne() {
    }

    @Test
    public void testSelectOne1() {
    }

    @Test
    public void testSelectOne2() {
    }

    @Test
    public void queryCount() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(TestDataUser.class);
        int i = sqlExecutor.queryCount(sqlExpression);

        Assert.assertTrue(i>0);
    }

    @Test
    public void testSelectList4() {
    }

    @Test
    public void testSelectList5() {
    }

    @Test
    public void queryModel() {
    }

    @Test
    public void queryList() {
    }

    @Test
    public void queryListEx() {
    }

    @Test
    public void testQueryList() {
    }

    @Test
    public void testQueryCount() {
    }

    @Test
    public void queryScalarDouble() {
    }
}