package com.cditer.free.data.test.dao.impl;

import com.cditer.free.core.util.PkIdUtils;
import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.test.dao.ITestDataUserDao;
import com.cditer.free.data.test.mapper.ITestDataUserMapper;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.test.viewmodel.TestDataUserViewEx;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestDataUserDaoImpl extends SuperDao<TestDataUser> implements ITestDataUserDao {

    @Autowired
    ITestDataUserMapper testDataUserMapper;

    public void test01(){
        TestDataUserViewEx dataUserView = new TestDataUserViewEx();
        dataUserView.setId("TEST_"+ PkIdUtils.getId());
        dataUserView.setName("chfree");
        dataUserView.setPassword("chfree");
        dataUserView.setAccount(PkIdUtils.getId());

        testDataUserMapper.insertListEx(Arrays.asList(dataUserView));
    }
}
