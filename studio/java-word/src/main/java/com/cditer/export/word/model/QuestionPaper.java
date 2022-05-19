package com.cditer.export.word.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionPaper {
    /**
     * 试卷标题
     */
    private String title;

    /**
     * 试卷副标题
     */
    private String subTitle;

    /**
     * 题目
     */
    private List<Topic> topics;

    /**
     * 页眉标题
     */
    private String headerTitle;
}
