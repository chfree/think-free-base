package com.tennetcn.free.data.demo.logical.mapper;

import com.tennetcn.free.data.dao.base.IMapper;
import com.tennetcn.free.data.demo.logical.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginUserMapper extends IMapper<LoginUser> {
}
