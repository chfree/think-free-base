package com.cditer.free.user.logical.entity.viewmodel;

import lombok.Data;

import java.util.Date;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-15 14:12:30
 * @comment     登陆授权表
 */

@Data
public class LoginAuthSearch{
    /**
     * 主键
     */
    private String id;

    /**
     * not-id
     */
    private String notId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * token信息
     */
    private String token;

    /**
     * 授权时间
     */
    private Date authTm;

    /**
     * 过期时间
     */
    private Date expTm;

    /**
     * 授权方式
     */
    private String type;

    /**
     * 状态
     */
    private String status;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

}