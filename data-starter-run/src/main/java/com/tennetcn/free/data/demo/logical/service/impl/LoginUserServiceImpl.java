package com.tennetcn.free.data.demo.logical.service.impl;

import com.tennetcn.free.data.dao.base.impl.SuperDao;
import com.tennetcn.free.data.demo.logical.model.LoginUser;
import com.tennetcn.free.data.demo.logical.service.ILoginUserService;
import org.springframework.stereotype.Component;

@Component
public class LoginUserServiceImpl extends SuperDao<LoginUser> implements ILoginUserService {
}
