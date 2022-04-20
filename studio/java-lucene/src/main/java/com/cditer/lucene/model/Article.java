package com.cditer.lucene.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 正文
     */
    private String content;

    /**
     * 状态
     */
    private String status;

    /**
     * 发表时间
     */
    private Date publishDate;

    /**
     * 来源
     */
    private String src;

    /**
     * tag
     */
    private List<String> tag;
}
