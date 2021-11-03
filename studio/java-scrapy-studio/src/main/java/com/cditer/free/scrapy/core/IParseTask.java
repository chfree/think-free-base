package com.cditer.free.scrapy.core;

import com.cditer.free.scrapy.message.PageInfo;
import com.cditer.free.scrapy.message.Task;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:25
 * @comment
 */

public interface IParseTask {
    List<PageInfo> parsePageTas(Task task);
}
