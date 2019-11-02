package com.tennetcn.free.data.demo.logical.model;

import com.tennetcn.free.core.message.ModelBase;
import com.tennetcn.free.core.message.OrderByEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.util.Date;

@Data
@Alias("loginUser")
@Entity
@Table(name="base_authority_user")
public class LoginUser extends ModelBase {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="mark_code")
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

    @Column(name="delete_mark")
    private Integer deleteMark;

    @OrderBy(value= OrderByEnum.DESC)
    @Column(name="create_date")
    private Date createDate;

    @Column(name="create_user_id")
    private String createUserId;

    @Column(name="create_user_name")
    private String createUserName;

    @Column(name="modify_date")
    private Date modifyDate;

    @Column(name="modify_user_id")
    private String modifyUserId;

    @Column(name="modify_user_name")
    private String modifyUserName;

    @Column(name="user_mark")
    private String userMark;

    @Column(name="is_locked")
    private Integer isLocked;

    @Column(name="is_login")
    private Integer isLogin;

    @Column(name="unique_mark")
    private String uniqueMark;

    @Column(name="bu_id")
    private String buId;
}
