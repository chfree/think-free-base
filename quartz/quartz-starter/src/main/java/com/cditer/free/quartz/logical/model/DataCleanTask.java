package com.cditer.free.quartz.logical.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import lombok.Data;
import com.cditer.free.core.message.data.ModelBase;

/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-12 12:44:16
 * @comment     数据清理任务
 */

@Data
@Entity
@Table(name="base_data_clean_task")
public class DataCleanTask extends ModelBase{
    /**
     * 主键
     */
    @Id
    @Column(name="id")
    private String id;

    /**
     * 名称
     */
    @Column(name="name")
    private String name;

    /**
     * 标题
     */
    @Column(name="title")
    private String title;

    /**
     * 类型
     */
    @Column(name="exec_type")
    private String execType;

    /**
     * 表名
     */
    @Column(name="table_name")
    private String tableName;

    /**
     * 过滤条件
     */
    @Column(name="filter")
    private String filter;

    /**
     * 状态
     */
    @Column(name="status")
    private String status;

    /**
     * 备注
     */
    @Column(name="comment")
    private String comment;

    /**
     * 备注1
     */
    @Column(name="rmrk1")
    private String rmrk1;

    /**
     * 备注2
     */
    @Column(name="rmrk2")
    private String rmrk2;

}
