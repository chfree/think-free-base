package com.tennetcn.free.data.demo.logical.model;

import com.tennetcn.free.data.message.ModelBase;
import com.tennetcn.free.data.message.OrderByEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.util.Date;

@Data
@Alias("loginUser")
@Entity
@Table(name="base_authority_login_user")
public class LoginUser extends ModelBase {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name="markCode")
    private String markCode;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

    @Column(name="sex")
    private String sex;

    @Column(name="theme")
    private String theme;

    @Column(name="mobile")
    private String mobile;

    @Column(name="email")
    private String email;

    @Column(name="deleteMark")
    private Integer deleteMark;

    @OrderBy(value= OrderByEnum.DESC)
    @Column(name="createDate")
    private Date createDate;

    @Column(name="createUserId")
    private String createUserId;

    @Column(name="createUserName")
    private String createUserName;

    @Column(name="modifyDate")
    private Date modifyDate;

    @Column(name="modifyUserId")
    private String modifyUserId;

    @Column(name="modifyUserName")
    private String modifyUserName;

    @Column(name="userMark")
    private String userMark;

    @Column(name="isLocked")
    private Integer isLocked;

    @Column(name="isLogin")
    private Integer isLogin;

    @Column(name="uniqueMark")
    private String uniqueMark;

    @Column(name="businessId")
    private String businessId;

    @Column(name="isAppLogin")
    private Integer isAppLogin;
}
