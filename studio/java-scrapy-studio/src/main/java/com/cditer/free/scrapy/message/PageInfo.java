package com.cditer.free.scrapy.message;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-11-03 12:21
 * @comment
 */

@Data
public class PageInfo {
    /**
     * 中文标题
     */
    private String title; //= scrapy.Field() # 中文标题
    private String authors; // = scrapy.Field() # 作者
    private String doi; // = scrapy.Field() # 期刊论文doi编码
    private String pdfUrl; // = scrapy.Field() # pdf文件地址
    private String wordUrl; // = scrapy.Field() # word文件地址

    private String keyWords; // = scrapy.Field() # 中文关键词
    private String cnAabstract; // = scrapy.Field() # 中文摘要
    private String enTitle; // = scrapy.Field() # 英文标题
    private String enKeyWords; // = scrapy.Field() # 英文关键词
    private String enAbstract; // = scrapy.Field() # 英文摘要
    private String authorOrganization; // = scrapy.Field() # 作者机构
    private String cite; // = scrapy.Field() # 文章引用
    private String content; // = scrapy.Field() # 论文内容
    private String reference; // = scrapy.Field() # 中文参考文献
    private String enReference; // = scrapy.Field() # 英文参考文献
    private String publishedDate; // = scrapy.Field() # 发版日期
    private String fund; // = scrapy.Field() # 论文基金
    private String source; //= scrapy.Field() # 来源
    private String theme; //= scrapy.Field() # 论文主题
    private String CLC; // = scrapy.Field() # 中图分类号
    private String Compendex; // = scrapy.Field() # 核心收录

    private String type; // = scrapy.Field() # 论文类型 1-期刊论文 2-学位论文 3-会议论文 .....
    private String field; // = scrapy.Field() # 论文所属领域 1-数学 2-医学 3-哲学 ....
    private String language; // = scrapy.Field() # 语言 中文-英文....
    private String authorName; // = scrapy.Field() # 作者中文名
    private String authorEnName; // = scrapy.Field() # 作者英文文名
    private String referenceFormat; // = scrapy.Field() # 文献格式 EndNote  BibTex
    private String qrCodeuUrl; // = scrapy.Field() # 二维码下载链接
    private String uploader; // = scrapy.Field() # 上传者
    private String likes; // = scrapy.Field() # 赞👍数
    private String steps; // = scrapy.Field() # 踩👎数
    private String collections; // = scrapy.Field() # 收藏♥数
    private String shares; // = scrapy.Field() # 分享数
    private String reports; // = scrapy.Field() # 举报数数
}
