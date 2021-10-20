package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.OrderByEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.core.util.ReflectUtils;
import com.cditer.free.data.message.OrderInfo;
import com.cditer.free.data.test.dao.ITestDataUserDao;
import com.cditer.free.data.test.model.TestDataUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

public class SuperDaoTest extends TestDataUserBase {

    @Autowired
    ITestDataUserDao testDataUserDao;


    @Test
    public void getMapper() {
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

    @Test
    public void addModel() {
        TestDataUser dataUser = new TestDataUser();
        dataUser.setId(CommonUtils.getShortUUID());
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
        dataUser.setId(CommonUtils.getShortUUID());
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
        dataUser.setId(CommonUtils.getShortUUID());
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
        dataUser.setId(CommonUtils.getShortUUID());
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
    }

    @Test
    public void testDeleteModel() {
    }

    @Test
    public void applyChange() {
    }

    @Test
    public void applyChanges() {
    }

    @Test
    public void update() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void testInsert() {
    }

    @Test
    public void selectList() {
    }

    @Test
    public void testSelectList() {
    }

    @Test
    public void testSelectList1() {
    }

    @Test
    public void selectListEx() {
    }

    @Test
    public void testSelectList2() {
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
    public void testSelectList3() {
    }

    @Test
    public void testSelectList4() {
    }

    @Test
    public void testSelectList5() {
    }

    @Test
    public void testQueryCount1() {
    }

    @Test
    public void testQueryModel1() {
    }

    @Test
    public void testQueryModel2() {
    }

    @Test
    public void testQueryList2() {
    }

    @Test
    public void testQueryList3() {
    }

    @Test
    public void testQueryList4() {
    }

    @Test
    public void queryListEx() {
    }

    @Test
    public void testQueryList5() {
    }

    @Test
    public void testQueryCount2() {
    }

    @Test
    public void testUpdate1() {
    }

    @Test
    public void testDelete1() {
    }

    @Test
    public void deleteByIds() {
    }

    @Test
    public void testDeleteByIds() {
    }

    @Test
    public void testDeleteByIds1() {
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