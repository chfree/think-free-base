package com.cditer.free.scrapy.core.impl;

import com.cditer.free.scrapy.message.ArticleInfo;
import com.cditer.free.scrapy.message.CaptureRule;
import com.cditer.free.scrapy.message.Filter;
import com.cditer.free.scrapy.message.FilterType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ParseTaskImplTest {
    ParseTaskImpl parseTask = new ParseTaskImpl();

    @Test
    public void doFilterTest(){
        CaptureRule captureRule = new CaptureRule();
        captureRule.setProperty("title");
        captureRule.addFilter(new Filter(FilterType.trim));

        ArticleInfo articleInfo = new ArticleInfo();
        parseTask.doFilter(captureRule, articleInfo, Arrays.asList("cheng "));

        Assert.assertEquals(articleInfo.getTitle(), "cheng");
    }

    @Test
    public void doFilterTest1(){
        CaptureRule captureRule = new CaptureRule();
        captureRule.setProperty("authors");
        captureRule.addFilter(new Filter(FilterType.split));

        ArticleInfo articleInfo = new ArticleInfo();
        parseTask.doFilter(captureRule, articleInfo, Arrays.asList("cheng","huan"));

        Assert.assertTrue(articleInfo.getAuthors().size()==2);
        Assert.assertEquals(String.join(",", articleInfo.getAuthors()), "cheng,huan");
    }
}