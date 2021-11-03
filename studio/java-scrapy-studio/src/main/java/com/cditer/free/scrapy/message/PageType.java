package com.cditer.free.scrapy.message;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:32
 * @comment
 */

public enum PageType {
    category_list,   // 目录list页面，比如每年有几期，每期里面才是文章list
    detailed_list,  // 文章list
    detailed // 文章详情页面
}
