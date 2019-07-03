package com.tennetcn.free.data.demo.logical.service.impl;

import com.tennetcn.free.data.dao.base.impl.SuperDao;
import com.tennetcn.free.data.demo.logical.mapper.LoginUserMapper;
import com.tennetcn.free.data.demo.logical.model.LoginUser;
import com.tennetcn.free.data.demo.logical.service.ILoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginUserServiceImpl extends SuperDao<LoginUser> implements ILoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public List<LoginUser> queryListMPByIds(List<String> ids) {
        return loginUserMapper.queryListMPByIds(ids);
    }
}
