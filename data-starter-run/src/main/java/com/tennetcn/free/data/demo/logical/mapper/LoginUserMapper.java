package com.tennetcn.free.data.demo.logical.mapper;

import com.tennetcn.free.data.dao.base.IMapper;
import com.tennetcn.free.data.demo.logical.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginUserMapper extends IMapper<LoginUser> {
    List<LoginUser> queryListMPByIds(List<String> ids);

    List<String> getLoginUserNamesByIds(List<String> ids);

    int testGlobal(@Param("loginUser") LoginUser loginUser);
}
