package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.util.PkIdUtils;
import com.cditer.free.data.test.service.ITestDataUserService;
import com.cditer.free.data.test.viewmodel.TestDataUserViewEx;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class SuperServiceTest extends TestDataUserBase {

    @Autowired
    ITestDataUserService testDataUserService;

    public void test01(){
        TestDataUserViewEx dataUserView = new TestDataUserViewEx();
        dataUserView.setId("TEST_"+ PkIdUtils.getId());
        dataUserView.setName("chfree");
        dataUserView.setPassword("chfree");
        dataUserView.setAccount(PkIdUtils.getId());

        testDataUserService.insertListEx(Arrays.asList(dataUserView));
    }
}
