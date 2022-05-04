package com.cditer.free.core.message.security;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-25 00:09
 * @comment
 */

@Data
public class LoginModel implements Serializable {
    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * token
     */
    private String token;

    /**
     * 角色
     */
    private String roleId;

    /**
     * 部门
     */
    private String deptId;
}
