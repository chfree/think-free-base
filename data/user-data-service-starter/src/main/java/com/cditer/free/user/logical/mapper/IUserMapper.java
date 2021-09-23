package com.cditer.free.user.logical.mapper;

import com.cditer.free.data.dao.base.IMapper;
import com.cditer.free.user.logical.entity.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-11 00:14
 * @comment
 */

@Mapper
public interface IUserMapper extends IMapper<User> {
    List<User> queryListMPByIds(List<String> ids);

    String getLoginUserNamesByIds(List<String> ids);
}
