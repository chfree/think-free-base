package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.enums.ModelStatus;
import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.OrderByEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.core.util.ReflectUtils;
import com.cditer.free.data.dao.base.IMapper;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.message.OrderInfo;
import com.cditer.free.data.test.dao.ITestDataUserDao;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.test.model.TestNoDbData;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SuperDaoTest extends TestDataUserBase {

    @Autowired
    ITestDataUserDao testDataUserDao;


    @Test
    public void getMapper() {
        IMapper<TestDataUser> mapper = testDataUserDao.getMapper();

        int i = mapper.selectCount(null);

        Assert.assertTrue(i>0);
    }

    @Test
    public void getModelName() {
        String modelName = testDataUserDao.getModelName();

        Assert.assertEquals(modelName, "TestDataUser");
    }

    @Test
    public void getTableName() {
        String tableName = testDataUserDao.getTableName();

        Assert.assertEquals(tableName, "test_authority_user");
    }

    @Test
    public void testGetTableName() {
        String tableName = testDataUserDao.getTableName(TestDataUser.class);

        Assert.assertEquals(tableName, "test_authority_user");
    }

    @Test
    public void getDbFirstColumnKey() {
        String dbFirstColumnKey = testDataUserDao.getDbFirstColumnKey();

        Assert.assertEquals(dbFirstColumnKey, "id");
    }

    @Test
    public void getOrderInfoList() {
        List<OrderInfo> orderInfoList = testDataUserDao.getOrderInfoList();

        Assert.assertTrue(orderInfoList.size()>0);

        Column fieldByAnno = ReflectUtils.getFieldByAnno(TestDataUser::getCreateDate, Column.class);

        Assert.assertEquals(orderInfoList.get(0).getProperty(), fieldByAnno.name());
        Assert.assertEquals(orderInfoList.get(0).getOrderBy(), OrderEnum.ASC);
    }

    @Test
    public void queryList() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList();

        Assert.assertTrue(testDataUsers.size()>0);
    }

    @Test
    public void queryListByIds() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(2, 1));
        List<String> ids = testDataUsers.stream().map(item -> item.getId()).collect(Collectors.toList());

        List<TestDataUser> testDataUsers1 = testDataUserDao.queryListByIds(ids);

        Assert.assertEquals(testDataUsers.size(), testDataUsers1.size());
        Assert.assertEquals(testDataUsers.get(0).getId(), testDataUsers1.get(0).getId());
    }

    @Test
    public void testQueryListByIds() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(2, 1));
        List<String> ids = testDataUsers.stream().map(item -> item.getId()).collect(Collectors.toList());

        List<TestDataUser> testDataUsers1 = testDataUserDao.queryListByIds(String.join(",", ids));

        Assert.assertEquals(testDataUsers.size(), testDataUsers1.size());
        Assert.assertEquals(testDataUsers.get(0).getId(), testDataUsers1.get(0).getId());
    }

    @Test
    public void testQueryListByIds1() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(2, 1));
        List<String> ids = testDataUsers.stream().map(item -> item.getId()).collect(Collectors.toList());

        List<TestDataUser> testDataUsers1 = testDataUserDao.queryListByIds(ids.get(0), ids.get(1));

        Assert.assertEquals(testDataUsers.size(), testDataUsers1.size());
        Assert.assertEquals(testDataUsers.get(0).getId(), testDataUsers1.get(0).getId());
    }

    @Test
    public void testQueryList() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(3, 1));

        Assert.assertTrue(testDataUsers.size()==3);
    }

    @Test
    public void testQueryList1() {
        TestDataUser search = new TestDataUser();
        search.setName("test0");

        List<TestDataUser> testDataUsers = testDataUserDao.queryList(search);

        Assert.assertTrue(testDataUsers.size()==1);

        Assert.assertEquals(search.getName(), testDataUsers.get(0).getName());
    }

    @Test
    public void queryModel() {
        TestDataUser search = new TestDataUser();
        search.setName("test0");

        TestDataUser testDataUser = testDataUserDao.queryModel(search);

        Assert.assertNotNull(testDataUser);

        Assert.assertEquals(search.getName(), testDataUser.getName());
    }

    @Test
    public void testQueryModel() {
        TestDataUser search = new TestDataUser();
        search.setName("test0");

        TestDataUser testDataUser = testDataUserDao.queryModel(search);

        TestDataUser testDataUser1 = testDataUserDao.queryModel(testDataUser.getId());

        Assert.assertEquals(testDataUser1.getName(), testDataUser.getName());
    }

    @Test
    public void queryCount() {
        int i = testDataUserDao.queryCount();

        Assert.assertTrue(i>0);
    }

    @Test
    public void testQueryCount() {
        TestDataUser search = new TestDataUser();
        search.setName("test0");

        int i = testDataUserDao.queryCount(search);

        Assert.assertTrue(i==1);
    }

    private String getTestUserId(){
        return "TEST_"+CommonUtils.getShortUUID();
    }

    @Test
    public void addModel() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(getTestUserId());
        dataUser.setAccount("cheng");
        dataUser.setPassword("000000");
        dataUser.setName("ch");

        boolean addResult = testDataUserDao.addModel(dataUser);
        Assert.assertTrue(addResult);

        TestDataUser testDataUser = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertNull(testDataUser.getCreateDate());
    }

    @Test
    public void addModelSelective() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(getTestUserId());
        dataUser.setAccount("cheng");
        dataUser.setPassword("000000");
        dataUser.setName("ch");

        boolean addResult = testDataUserDao.addModelSelective(dataUser);
        Assert.assertTrue(addResult);

        TestDataUser testDataUser = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertNotNull(testDataUser.getCreateDate());
    }

    @Test
    public void updateModel() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(getTestUserId());
        dataUser.setAccount("cheng");
        dataUser.setPassword("000000");
        dataUser.setName("ch");
        dataUser.setBuId("123");

        boolean addResult = testDataUserDao.addModel(dataUser);
        Assert.assertTrue(addResult);

        dataUser.setName("chfree");
        dataUser.setBuId(null);
        boolean updateResult = testDataUserDao.updateModel(dataUser);
        Assert.assertTrue(updateResult);

        TestDataUser testDataUser = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertNull(testDataUser.getBuId());
        Assert.assertEquals(testDataUser.getName(), dataUser.getName());
    }

    @Test
    public void updateModelSelective() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(getTestUserId());
        dataUser.setAccount("cheng");
        dataUser.setPassword("000000");
        dataUser.setName("ch");
        dataUser.setBuId("123");

        boolean addResult = testDataUserDao.addModel(dataUser);
        Assert.assertTrue(addResult);

        dataUser.setName("chfree");
        dataUser.setBuId(null);
        boolean updateResult = testDataUserDao.updateModelSelective(dataUser);
        Assert.assertTrue(updateResult);

        TestDataUser testDataUser = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertNotNull(testDataUser.getBuId());
        Assert.assertEquals(testDataUser.getName(), dataUser.getName());
    }

    @Test
    public void deleteModel() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(5, 1));

        TestDataUser testDataUser = testDataUserDao.queryModel(testDataUsers.get(0).getId());
        Assert.assertNotNull(testDataUser);

        boolean result = testDataUserDao.deleteModel(testDataUsers.get(0).getId());
        Assert.assertTrue(result);

        testDataUser = testDataUserDao.queryModel(testDataUsers.get(0).getId());
        Assert.assertNull(testDataUser);
    }

    @Test
    public void testDeleteModel() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(5, 1));

        TestDataUser testDataUser = testDataUserDao.queryModel(testDataUsers.get(0).getId());
        Assert.assertNotNull(testDataUser);

        boolean result = testDataUserDao.deleteModel(testDataUsers.get(0));
        Assert.assertTrue(result);

        testDataUser = testDataUserDao.queryModel(testDataUsers.get(0).getId());
        Assert.assertNull(testDataUser);
    }

    @Test
    public void applyChange() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(getTestUserId());
        dataUser.setAccount("cheng");
        dataUser.setPassword("000000");
        dataUser.setName("ch");
        dataUser.setBuId("123");
        dataUser.setModelStatus(ModelStatus.add);

        boolean result = testDataUserDao.applyChange(dataUser);
        Assert.assertTrue(result);

        TestDataUser testDataUser = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertNotNull(testDataUser);

        testDataUser.setName("CH");
        testDataUser.setModelStatus(ModelStatus.update);
        result = testDataUserDao.applyChange(testDataUser);
        Assert.assertTrue(result);

        testDataUser = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertEquals("CH", testDataUser.getName());
    }

    @Test
    public void applyChanges() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(getTestUserId());
        dataUser.setAccount("cheng");
        dataUser.setPassword("000000");
        dataUser.setName("ch");
        dataUser.setBuId("123");
        dataUser.setModelStatus(ModelStatus.add);

        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);
        testDataUser.setModelStatus(ModelStatus.update);
        testDataUser.setName("updCh");

        testDataUsers.add(dataUser);

        boolean b = testDataUserDao.applyChanges(testDataUsers);
        Assert.assertTrue(b);

        TestDataUser addOfDb = testDataUserDao.queryModel(dataUser.getId());
        Assert.assertEquals(addOfDb.getName(), dataUser.getName());

        TestDataUser updOfDb = testDataUserDao.queryModel(testDataUser.getId());
        Assert.assertEquals(updOfDb.getName(), testDataUser.getName());
    }

    @Test
    public void update() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression updateSql = SqlExpressionFactory.createExpression();
        updateSql.update(TestDataUser.class).setColumn(TestDataUser::getName, "CH").andEq(TestDataUser::getId, testDataUser.getId());

        int update = testDataUserDao.update(updateSql);
        Assert.assertEquals(update, 1);

        TestDataUser updOfDb = testDataUserDao.queryModel(testDataUser.getId());
        Assert.assertEquals(updOfDb.getName(), "CH");
    }

    @Test
    public void testUpdate() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression updateSql = SqlExpressionFactory.createExpression();
        updateSql.update(TestDataUser.class).setColumn(TestDataUser::getName, "CH").andEq(TestDataUser::getId, testDataUser.getId());

        int update = testDataUserDao.update(updateSql.toSql(), updateSql.getParams());
        Assert.assertEquals(update, 1);

        TestDataUser updOfDb = testDataUserDao.queryModel(testDataUser.getId());
        Assert.assertEquals(updOfDb.getName(), "CH");
    }

    @Test
    public void delete() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression deleteSql = SqlExpressionFactory.createExpression();
        deleteSql.delete().from(TestDataUser.class).andEq(TestDataUser::getId, testDataUser.getId());

        int update = testDataUserDao.delete(deleteSql);
        Assert.assertEquals(update, 1);

        TestDataUser updOfDb = testDataUserDao.queryModel(testDataUser.getId());
        Assert.assertNull(updOfDb);
    }

    @Test
    public void testDelete() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression deleteSql = SqlExpressionFactory.createExpression();
        deleteSql.delete().from(TestDataUser.class).andEq(TestDataUser::getId, testDataUser.getId());

        int update = testDataUserDao.delete(deleteSql.toSql(), deleteSql.getParams());
        Assert.assertEquals(update, 1);

        TestDataUser updOfDb = testDataUserDao.queryModel(testDataUser.getId());
        Assert.assertNull(updOfDb);
    }

    @Test
    public void insert() {
        ISqlExpression insertSql = SqlExpressionFactory.createExpression();
        String testUserId = getTestUserId();
        insertSql.insert(TestDataUser.class)
                .insertColumn(TestDataUser::getId, testUserId)
                .insertColumn(TestDataUser::getName, "CH")
                .insertColumn(TestDataUser::getAccount, "cheng")
                .insertColumn(TestDataUser::getPassword, "000000")
                .insertColumn(TestDataUser::getBuId, "123");

        int insert = testDataUserDao.insert(insertSql);
        Assert.assertEquals(insert, 1);

        TestDataUser insertOfDb = testDataUserDao.queryModel(testUserId);
        Assert.assertEquals(insertOfDb.getName(), "CH");
        Assert.assertEquals(insertOfDb.getId(), testUserId);
    }

    @Test
    public void testInsert() {
        ISqlExpression insertSql = SqlExpressionFactory.createExpression();
        String testUserId = getTestUserId();
        insertSql.insert(TestDataUser.class)
                .insertColumn(TestDataUser::getId, testUserId)
                .insertColumn(TestDataUser::getName, "CH")
                .insertColumn(TestDataUser::getAccount, "cheng")
                .insertColumn(TestDataUser::getPassword, "000000")
                .insertColumn(TestDataUser::getBuId, "123");

        int insert = testDataUserDao.insert(insertSql.toSql(), insertSql.getParams());
        Assert.assertEquals(insert, 1);

        TestDataUser insertOfDb = testDataUserDao.queryModel(testUserId);
        Assert.assertEquals(insertOfDb.getName(), "CH");
        Assert.assertEquals(insertOfDb.getId(), testUserId);
    }

    @Test
<<<<<<< HEAD
    public void selectOne() {
        
    }
=======
    public void testQueryCount1() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);
>>>>>>> b06f5a042be0a30e18015e9ec5be4d3508795ddd

        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(TestDataUser.class).andEq(TestDataUser::getId, testDataUser.getId());

        int i = testDataUserDao.queryCount(sqlExpression);

        Assert.assertTrue(i==1);
    }

    @Test
    public void testQueryModel1() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression queryModelSql = SqlExpressionFactory.createExpression();
        queryModelSql.selectAllFrom(TestDataUser.class).andEq(TestDataUser::getId, testDataUser.getId());

        TestDataUser testDataUser1 = testDataUserDao.queryModel(queryModelSql);

        Assert.assertEquals(testDataUser.getId(), testDataUser1.getId());
    }

    @Test
    public void testQueryModel2() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression queryModelSql = SqlExpressionFactory.createExpression();
        queryModelSql.selectAllFrom(TestDataUser.class).andEq(TestDataUser::getId, testDataUser.getId());

        Map<String, Object> stringObjectMap = testDataUserDao.queryModelEx(queryModelSql);

        Assert.assertEquals(testDataUser.getId(), stringObjectMap.get("id"));
    }

    @Test
    public void testQueryList2() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        List<TestDataUser> testDataUsers = testDataUserDao.queryList(sqlExpression);
        Assert.assertTrue(testDataUsers.size()>0);
    }

    @Test
    public void testQueryList3() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        List<TestDataUser> testDataUsers = testDataUserDao.queryList(sqlExpression, new PagerModel(3, 1));
        Assert.assertTrue(testDataUsers.size()==3);
    }

    @Test
    public void testQueryList4() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        List<TestNoDbData> testDataUsers = testDataUserDao.queryList(sqlExpression, TestNoDbData.class);

        Assert.assertTrue(testDataUsers.size()>0);
        Assert.assertNotNull(testDataUsers.get(0).getName());
    }

    @Test
    public void queryListEx() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        List<Map<String, Object>> maps = testDataUserDao.queryListEx(sqlExpression, new PagerModel(3, 1));
        Assert.assertTrue(maps.size()==3);

        Assert.assertNotNull(maps.get(0).get("name"));
    }

    @Test
    public void testQueryList5() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(TestDataUser.class);

        List<TestNoDbData> testDataUsers = testDataUserDao.queryList(sqlExpression, new PagerModel(3, 1), TestNoDbData.class);

        Assert.assertTrue(testDataUsers.size()==3);
        Assert.assertNotNull(testDataUsers.get(0).getName());
    }

    @Test
    public void testQueryCount2() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(1, 1));
        TestDataUser testDataUser = testDataUsers.get(0);

        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(TestDataUser.class).andEq(TestDataUser::getId, testDataUser.getId());

        int i = testDataUserDao.queryCount(sqlExpression.toSql(), sqlExpression.getParams());

        Assert.assertTrue(i==1);
    }

    @Test
    public void deleteByIds() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(2, 1));

        List<TestDataUser> testDataUsers1 = testDataUserDao.queryListByIds(testDataUsers.get(0).getId(), testDataUsers.get(1).getId());
        Assert.assertTrue(!CollectionUtils.isEmpty(testDataUsers1));

        testDataUserDao.deleteByIds(testDataUsers.get(0).getId(), testDataUsers.get(1).getId());

        testDataUsers1 = testDataUserDao.queryListByIds(testDataUsers.get(0).getId(), testDataUsers.get(1).getId());
        Assert.assertTrue(CollectionUtils.isEmpty(testDataUsers1));
    }

    @Test
    public void testDeleteByIds() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(2, 1));
        List<String> ids = testDataUsers.stream().map(item -> item.getId()).collect(Collectors.toList());


        List<TestDataUser> testDataUsers1 = testDataUserDao.queryListByIds(ids);
        Assert.assertTrue(!CollectionUtils.isEmpty(testDataUsers1));

        testDataUserDao.deleteByIds(ids);

        testDataUsers1 = testDataUserDao.queryListByIds(ids);
        Assert.assertTrue(CollectionUtils.isEmpty(testDataUsers1));
    }

    @Test
    public void testDeleteByIds1() {
        List<TestDataUser> testDataUsers = testDataUserDao.queryList(new PagerModel(2, 1));
        List<String> ids = testDataUsers.stream().map(item -> item.getId()).collect(Collectors.toList());


        List<TestDataUser> testDataUsers1 = testDataUserDao.queryListByIds(String.join(",", ids));
        Assert.assertTrue(!CollectionUtils.isEmpty(testDataUsers1));

        testDataUserDao.deleteByIds(String.join(",", ids));

        testDataUsers1 = testDataUserDao.queryListByIds(String.join(",", ids));
        Assert.assertTrue(CollectionUtils.isEmpty(testDataUsers1));
    }

    @Test
    public void testInsert1() {
    }

    @Test
    public void queryScalarDouble() {
    }

    @Test
    public void queryScalarInt() {
    }

    @Test
    public void queryScalar() {
    }

    @Test
    public void insertListEx() {
    }

    @Test
    public void testInsertListEx() {
    }

    @Test
    public void batchInsertList() {
    }

    @Test
    public void testBatchInsertList() {
    }

    @Test
    public void batchUpdateList() {
    }

    @Test
    public void batchInsertSelectiveList() {
    }

    @Test
    public void testBatchInsertSelectiveList() {
    }

    @Test
    public void batchUpdateSelectiveList() {
    }
}