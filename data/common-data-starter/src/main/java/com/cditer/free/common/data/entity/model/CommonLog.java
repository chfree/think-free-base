package com.cditer.free.common.data.entity.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import lombok.Data;
import com.cditer.free.core.message.data.ModelBase;

/**
 * @author      C.H
 * @email       chfree365@qq.com
 * @createtime  2024-08-16 12:15:10
 * @comment     通用日志
 */

@Data
@Entity
@Table(name="base_common_log")
public class CommonLog extends ModelBase{
    /**
     * 主键
     */
    @Id
    @Column(name="id")
    private String id;

    /**
     * 业务id
     */
    @Column(name="bsn_id")
    private String bsnId;

    /**
     * 短消息
     */
    @Column(name="short_message")
    private String shortMessage;

    /**
     * 消息
     */
    @Column(name="message")
    private String message;

    /**
     * 业务类型
     */
    @Column(name="bsn_type")
    private String bsnType;

    /**
     * 子类型
     */
    @Column(name="sub_type")
    private String subType;

    /**
     * 第三类型
     */
    @Column(name="thd_type")
    private String thdType;

    /**
     * 记录开始时间
     */
    @Column(name="record_start_time")
    private Date recordStartTime;

    /**
     * 记录结束时间
     */
    @Column(name="record_end_time")
    private Date recordEndTime;

    /**
     * 日志类型
     */
    @Column(name="log_type")
    private String logType;

    /**
     * 日志状态
     */
    @Column(name="log_status")
    private String logStatus;

    /**
     * 用户id
     */
    @Column(name="user_id")
    private String userId;

    /**
     * 部门id
     */
    @Column(name="dept_id")
    private String deptId;

    /**
     * 角色id
     */
    @Column(name="role_id")
    private String roleId;

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
