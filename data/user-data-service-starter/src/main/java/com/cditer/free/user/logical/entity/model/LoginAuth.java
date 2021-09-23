package com.cditer.free.user.logical.entity.model;

import com.cditer.free.core.message.data.ModelBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-15 14:10:26
 * @comment     登陆授权表
 */

@Data
@Entity
@Table(name="base_authority_auth")
public class LoginAuth extends ModelBase{
    /**
     * 主键
     */
    @Id
    @Column(name="id")
    private String id;

    /**
     * 用户id
     */
    @Column(name="user_id")
    private String userId;

    /**
     * token信息
     */
    @Column(name="token")
    private String token;

    /**
     * 授权时间
     */
    @Column(name="auth_tm")
    private Date authTm;

    /**
     * 过期时间
     */
    @Column(name="exp_tm")
    private Date expTm;

    /**
     * 授权方式
     */
    @Column(name="type")
    private String type;

    /**
     * 状态
     */
    @Column(name="status")
    private String status;

}