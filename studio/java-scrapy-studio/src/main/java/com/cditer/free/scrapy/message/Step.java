package com.cditer.free.scrapy.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:21
 * @comment
 */

@Data
public class Step {
    /**
     * 步骤顺序
     */
    private int order;

    /**
     * 页面类型
     */
    private PageType pageType;

    /**
     * 如果是list类型，则有循环的match
     */
    private String listMatch;

    /**
     * 抓取规则
     */
    @Setter(AccessLevel.NONE)
    private List<CaptureRule> captureRules = new ArrayList<>();

    public void addRule(CaptureRule rule){
        captureRules.add(rule);
    }
}
