package com.cditer.free.data.test.dao.impl;

import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.test.dao.ITestDataUserDao;
import com.cditer.free.data.test.model.TestDataUser;
import org.springframework.stereotype.Component;

@Component
public class TestDataUserDaoImpl extends SuperDao<TestDataUser> implements ITestDataUserDao {
}
