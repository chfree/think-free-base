package com.tennetcn.free.data.demo.logical.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.tennetcn.free.core.message.ModelBase;
import com.tennetcn.free.core.message.OrderByEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author      程欢
 * @email       79763939@qq.com
 * @createtime  2017年03月28日 11:59:09
 * @comment
 */
@Data
@Alias("business")
@Entity
@Table(name="base_authority_business")
public class Business extends ModelBase {
    //主键
    @Id
    @Column(name="buId")
    private String buId;

    //商业单位名称
    @Column(name="name")
    private String name;

    //性质
    @Column(name="nature")
    private String nature;

    //简称
    @Column(name="shortName")
    private String shortName;

    //编码
    @Column(name="code")
    private String code;

    //助记码
    @Column(name="markCode")
    private String markCode;

    //地址
    @Column(name="address")
    private String address;

    //电话
    @Column(name="phone")
    private String phone;

    //排序字段
    @OrderBy(value= OrderByEnum.ASC)
    @Column(name="sortCode")
    private int sortCode;

    //是否删除
    @Column(name="deleteMark")
    private int deleteMark;

    //创建时间
    @Column(name="createDate")
    private Date createDate;

    //创建人id
    @Column(name="createUserId")
    private String createUserId;

    //创建人名称
    @Column(name="createUserName")
    private String createUserName;

    //修改时间
    @Column(name="modifyDate")
    private Date modifyDate;

    //修改人id
    @Column(name="modifyUserId")
    private String modifyUserId;

    //修改人名称
    @Column(name="modifyUserName")
    private String modifyUserName;

    //备注
    @Column(name="comments")
    private String comments;
}