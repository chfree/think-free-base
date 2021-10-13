package com.cditer.free.data.test.model;

import com.cditer.free.core.message.data.OrderByEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "test_authority_user")
public class TestDataUser {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

    @Column(name="delete_mark")
    private Integer deleteMark;

    @OrderBy(value= OrderByEnum.ASC)
    @Column(name="create_date")
    private Date createDate;

    @Column(name="bu_id")
    private String buId;
}
