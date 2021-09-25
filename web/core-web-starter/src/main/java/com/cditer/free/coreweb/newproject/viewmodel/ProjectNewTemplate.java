package com.cditer.free.coreweb.newproject.viewmodel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectNewTemplate {
    /**
     * spring boot 版本
     */
    private String springBootVersion;

    /**
     * free base版本
     */
    private String freeVersion;

    /**
     * groppId
     */
    private String groupId;

    /**
     * artifactId
     */
    private String artifactId;

    /**
     * version
     */
    private String version;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 作者
     */
    private String author;

    /**
     * email
     */
    private String email;

    /**
     * java version
     */
    private String javaVersion;

    /**
     * 项目描述
     */
    private String description;

    /**
     * curentDate
     */
    private Date currentDate = DateUtil.date();

    // 项目名称
    private String projectName;

    /**
     * 当前路径
     */
    private String uuid = IdUtil.fastUUID();

    public String getUserPath(){
        return uuid;
    }
}
