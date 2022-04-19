package com.cditer.lucene.model;

import lombok.Data;

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
}
