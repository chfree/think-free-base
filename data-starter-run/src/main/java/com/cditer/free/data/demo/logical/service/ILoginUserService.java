package com.cditer.free.data.demo.logical.service;

import com.cditer.free.data.dao.base.ISuperDao;
import com.cditer.free.data.demo.logical.model.LoginUser;
import com.cditer.free.data.demo.logical.viewmodel.TestUser;
import com.cditer.free.data.demo.logical.viewmodel.TestUser1;

import java.util.List;

public interface ILoginUserService extends ISuperDao<LoginUser> {
    List<LoginUser> queryListMPByIds(List<String> ids);

    List<LoginUser> queryTest();

    List<TestUser> queryTestUsers();

    List<TestUser> queryTestUserxs();

    List<TestUser1> queryTestUsers1();

    void batchInsert(List<LoginUser> users);

    int querySeq(String seqName);

    String queryAllParentDeptById(String id);

}
