package com.tennetcn.free.data.demo.logical.service;

import com.tennetcn.free.data.dao.base.ISuperDao;
import com.tennetcn.free.data.demo.logical.model.LoginUser;

import java.util.List;

public interface ILoginUserService extends ISuperDao<LoginUser> {
    List<LoginUser> queryListMPByIds(List<String> ids);
}
