package com.cditer.free.data.test.mapper;

import com.cditer.free.data.dao.base.IMapper;
import com.cditer.free.data.test.model.TestDataUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ITestDataUserMapper extends IMapper<TestDataUser> {
}
