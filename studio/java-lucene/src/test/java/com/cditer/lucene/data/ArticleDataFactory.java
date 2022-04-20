package com.cditer.lucene.data;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.cditer.lucene.model.Article;

import java.nio.charset.Charset;
import java.util.List;

public class ArticleDataFactory {
    public static List<Article> buildArticles(){
        String path = ArticleDataFactory.class.getClassLoader().getResource(String.format("article.test.json")).getPath();
        String json = FileUtil.readString(path, Charset.defaultCharset());
        return JSONUtil.toList(JSONUtil.parseArray(json), Article.class);
    }

}
