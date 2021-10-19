package com.cditer.free.data.dao.base.impl;

import cn.hutool.core.date.DateUtil;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.data.BaseTest;
import com.cditer.free.data.dao.base.ISqlExecutor;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.test.mapper.ITestDataUserMapper;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.utils.Pager2RowBounds;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class SqlExecutorImplTest extends TestDataUserBase {

    @Autowired
    ISqlExecutor sqlExecutor;

    @Autowired
    private ITestDataUserMapper testDataUserMapper;


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
        List<TestDataUser> list = testDataUserMapper.select(null);
        TestDataUser testDataUser = list.get(0);

        ISqlExpression delSql = SqlExpressionFactory.createExpression();
        delSql.delete().from(TestDataUser.class).andEqNoEmpty(TestDataUser::getId, testDataUser.getId());

        int delete = sqlExecutor.delete(delSql);

        Assert.assertTrue(delete == 1);
    }

    @Test
    public void insert() {
        ISqlExpression insertSql = SqlExpressionFactory.createExpression();

        insertSql.insert(TestDataUser.class)
                .insertColumn(TestDataUser::getId, "TEST_" + CommonUtils.getShortUUID())
                .insertColumn(TestDataUser::getName, "cheng")
                .insertColumn(TestDataUser::getPassword, "000000")
                .insertColumn(TestDataUser::getCreateDate, DateUtil.date())
                .insertColumn(TestDataUser::getAccount, "cheng");

        int insert = sqlExecutor.insert(insertSql);

        Assert.assertTrue(insert==1);
    }

    @Test
    public void queryScalar() {
        ISqlExpression scalarSql = SqlExpressionFactory.createExpression();
        scalarSql.select(TestDataUser::getName).from(TestDataUser.class).andEq(TestDataUser::getName, "test0");

        String scalarVal = sqlExecutor.selectScalar(scalarSql);

        Assert.assertEquals(scalarVal, "test0");
    }

    @Test
    public void queryScalarInt() {
        ISqlExpression scalarSql = SqlExpressionFactory.createExpression();
        scalarSql.selectCount().from(TestDataUser.class).andEq(TestDataUser::getName, "test0");

        int scalarVal = sqlExecutor.selectScalarInt(scalarSql);

        Assert.assertEquals(scalarVal, 1);
    }

    @Test
    public void selectListEx() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class).andEq(TestDataUser::getName, "test0");;

        List<Map<String, Object>> maps = sqlExecutor.selectListEx(sqlExpression);

        Assert.assertEquals(maps.get(0).get("name"), "test0");
    }

    @Test
    public void selectList() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class).andEq(TestDataUser::getName, "test0");

        List<TestDataUser> testDataUsers = sqlExecutor.selectList(sqlExpression, TestDataUser.class);
        Assert.assertTrue(testDataUsers.size()==1);

        Assert.assertEquals(testDataUsers.get(0).getName(), "test0");
    }

    @Test
    public void testSelectList3() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        PagerModel pagerModel = new PagerModel(1,1);
        List<TestDataUser> testDataUsers = sqlExecutor.selectList(sqlExpression, Pager2RowBounds.getRowBounds(pagerModel), TestDataUser.class);

        Assert.assertTrue(testDataUsers.size()==1);
    }

    @Test
    public void selectOne() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        Assert.assertThrows(TooManyResultsException.class, ()->{
            sqlExecutor.selectOneEx(sqlExpression);
        });

        Assert.assertThrows(TooManyResultsException.class, ()->{
            sqlExecutor.selectOne(sqlExpression, TestDataUser.class);
        });

        sqlExpression.andEq(TestDataUser::getName, "test0");

        Map<String, Object> stringObjectMap = sqlExecutor.selectOneEx(sqlExpression);
        Assert.assertEquals(stringObjectMap.get("name"), "test0");

        TestDataUser testDataUser = sqlExecutor.selectOne(sqlExpression, TestDataUser.class);
        Assert.assertEquals(testDataUser.getName(), "test0");
    }

    @Test
    public void selectCount() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(TestDataUser.class);
        int i = sqlExecutor.selectCount(sqlExpression);

        Assert.assertTrue(i > 0);
    }

    @Test
    public void testSelectList4() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class).andEq(TestDataUser::getName, "test0");

        List<TestDataUser> testDataUsers = sqlExecutor.selectList(sqlExpression, TestDataUser.class);

        Assert.assertTrue(testDataUsers.size()==1);
        Assert.assertEquals(testDataUsers.get(0).getName(), "test0");
    }

    @Test
    public void testSelectList5() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        PagerModel pagerModel = new PagerModel(5,1);

        List<TestDataUser> testDataUsers = sqlExecutor.selectList(sqlExpression, pagerModel, TestDataUser.class);

        Assert.assertTrue(testDataUsers.size()==5);
        Assert.assertNotNull(testDataUsers.get(0).getName());
    }

    @Test
    public void queryListEx() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        PagerModel pagerModel = new PagerModel(5,1);
        List<Map<String, Object>> maps = sqlExecutor.selectListEx(sqlExpression, pagerModel);

        Assert.assertTrue(maps.size()==5);
        Assert.assertNotNull(maps.get(0).get("name"));

    }

    @Test
    public void queryScalarDouble() {
        ISqlExpression scalarSql = SqlExpressionFactory.createExpression();
        scalarSql.selectCount().from(TestDataUser.class).andEq(TestDataUser::getName, "test0");

        Double scalarVal = sqlExecutor.selectScalarDouble(scalarSql);
        Double result = 1.0;

        Assert.assertEquals(scalarVal, result);
    }
}