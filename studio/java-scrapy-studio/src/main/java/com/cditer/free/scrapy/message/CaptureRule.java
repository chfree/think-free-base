package com.cditer.free.scrapy.message;

import lombok.Data;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:35
 * @comment
 */

@Data
public class CaptureRule {
    /**
     * 设置的对象属性
     */
    private String property;

    /**
     * 规则
     */
    private String matchRule;

    /**
     * 规则提取属性,为空则是取text
     */
    private String attribute;

    /**
     * 过滤器，提供一些过滤规则
     */
    private List<Filter> filters;

    public CaptureRule(){}

    public CaptureRule(String property, String matchRule){
        this.property = property;
        this.matchRule = matchRule;
    }

    public CaptureRule(String property, String matchRule,List<Filter> filters){
        this.property = property;
        this.matchRule = matchRule;
        this.filters = filters;
    }

    public CaptureRule(String property, String matchRule,String attribute){
        this.property = property;
        this.matchRule = matchRule;
        this.attribute = attribute;
    }

    public CaptureRule(String property, String matchRule,String attribute,List<Filter> filters){
        this.property = property;
        this.matchRule = matchRule;
        this.attribute = attribute;
        this.filters = filters;
    }
}
