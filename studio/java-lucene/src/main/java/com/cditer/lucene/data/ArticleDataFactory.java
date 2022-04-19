package com.cditer.lucene.data;

import com.cditer.lucene.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDataFactory {
    public static List<Article> buildArticles(){
        List<Article> list = new ArrayList<>();

        Article article = new Article();
        article.setId("001");
        article.setTitle("这是一篇文章");
        article.setSummary("这是一篇文章");


        return list;
    }

}
