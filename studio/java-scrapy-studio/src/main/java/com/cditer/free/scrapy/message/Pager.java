package com.cditer.free.scrapy.message;

import lombok.Data;

@Data
public class Pager {
    /**
     * 下一页的规则
     */
    private CaptureRule nextPageMatch;
}
