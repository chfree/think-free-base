package com.cditer.free.scrapy.message;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:35
 * @comment
 */

@Data
public class CaptureRule {
    private String property;

    private String matchRule;

    public CaptureRule(){}

    public CaptureRule(String property, String matchRule){
        this.property = property;
        this.matchRule = matchRule;
    }
}
