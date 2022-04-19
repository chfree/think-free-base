package com.cditer.lucene.base;

import com.cditer.lucene.data.ArticleDataFactory;
import com.cditer.lucene.model.Article;
import org.apache.lucene.document.Document;
import org.junit.Test;

import java.util.List;

public class FirstExample {

    @Test
    public void createIndexDb(){
        List<Article> list = ArticleDataFactory.buildArticles();

        Article article = list.get(0);

        // 创建Document对象
        Document document = new Document();
    }
}
