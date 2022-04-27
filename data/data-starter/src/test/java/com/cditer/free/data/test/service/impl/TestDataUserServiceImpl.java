package com.cditer.free.data.test.service.impl;

import com.cditer.free.core.util.PkIdUtils;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.test.service.ITestDataUserService;
import com.cditer.free.data.test.viewmodel.TestDataUserViewEx;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestDataUserServiceImpl extends SuperService<TestDataUser> implements ITestDataUserService {

    public void test01(){
        TestDataUserViewEx dataUserView = new TestDataUserViewEx();
        dataUserView.setId("TEST_"+ PkIdUtils.getId());
        dataUserView.setName("chfree");
        dataUserView.setPassword("chfree");
        dataUserView.setAccount(PkIdUtils.getId());

        insertListEx(Arrays.asList(dataUserView));
    }
}
