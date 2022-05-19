package com.cditer.export.word.model;

import lombok.Data;

import java.util.List;

@Data
public class Topic {
    /**
     * 题目序号
     */
    private int seq;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 图片资源
     */
    private List<String> imgs;

    /**
     * 图片布局格式
     */
    private int imgLayout;

    /**
     * 下间距
     */
    private int downSpace;

    /**
     * 上间距
     */
    private int upSpace;

    private boolean isFirst() {
        return seq == 1;
    }
}
