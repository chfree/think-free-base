package com.cditer.free.scrapy.message;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:22
 * @comment
 */

@Data
public class Start {
    /**
     * 搜索的网址
     */
    private String url;

    /**
     * 网址描述
     */
    private String title;
}
