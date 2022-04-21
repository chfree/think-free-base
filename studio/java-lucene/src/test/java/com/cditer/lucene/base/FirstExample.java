package com.cditer.lucene.base;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.cditer.free.core.exception.BizException;
import com.cditer.lucene.data.ArticleDataFactory;
import com.cditer.lucene.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKSynonymAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FirstExample {

    private static final String path = "E:\\document\\testfile\\lucene\\test\\article";

    @Test
    public void queryArticleBoolQueryTest() {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        // 精确查找
        TermQuery termQuery = new TermQuery(new Term("id", "001"));
        builder.add(termQuery, BooleanClause.Occur.MUST);
        // 接一个模糊查找
        booleanQueryAddNotEmpty(builder, "title", "java JReleaser");

        List<Article> articles = getArticle(builder.build());

        Assert.assertTrue(articles.size() == 1);
        Assert.assertEquals("001", articles.get(0).getId());
    }

    @Test
    public void queryArticleTermTest() {
        // 精确查找
        TermQuery termQuery = new TermQuery(new Term("id", "001"));
        List<Article> articles = getArticle(termQuery);

        Assert.assertTrue(articles.size() == 1);
        Assert.assertEquals("001", articles.get(0).getId());

        termQuery = new TermQuery(new Term("id", "a001"));
        articles = getArticle(termQuery);

        Assert.assertTrue(CollectionUtils.isEmpty(articles));
    }

    @Test
    public void queryArticleInTest() {
        // in查找
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        booleanQueryAddNotEmpty(builder, "id", "001", BooleanClause.Occur.SHOULD);
        booleanQueryAddNotEmpty(builder, "id", "004", BooleanClause.Occur.SHOULD);

        List<Article> articles = getArticle(builder.build());

        Assert.assertTrue(articles.size() == 2);
        Assert.assertEquals("001", articles.get(0).getId());


        builder = new BooleanQuery.Builder();
        booleanQueryAddNotEmpty(builder, "id", "001", BooleanClause.Occur.SHOULD);
        booleanQueryAddNotEmpty(builder, "id", "04", BooleanClause.Occur.SHOULD);

        articles = getArticle(builder.build());

        Assert.assertTrue(articles.size() == 1);
        Assert.assertEquals("001", articles.get(0).getId());

    }

    private List<Article> getArticle(Query query) {
        IndexReader indexReader = null;
        Directory directory = null;
        List<Article> resultList = new ArrayList<>();
        try {
            directory = FSDirectory.open(Paths.get(path));
            indexReader = DirectoryReader.open(directory);

            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            indexSearcher.setSimilarity(new ClassicSimilarity());

            Sort sort = Sort.RELEVANCE;
            ScoreDoc[] docs = indexSearcher.search(query, 10, sort, true).scoreDocs;

            for (ScoreDoc doc : docs) {
                Document document = indexSearcher.doc(doc.doc);
                Article dto = getLuceneModel(document);

                resultList.add(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexReader != null) {
                try {
                    indexReader.close();
                } catch (Exception e) {
                    log.error("关闭indexReader发生异常", e);
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (Exception e) {
                    log.error("模糊搜索发生异常：", e);
                }
            }
        }

        return resultList;
    }


    private Article getLuceneModel(Document document) throws IOException {
        Article dto = new Article();

        dto.setId(document.get("id"));
        dto.setSummary(document.get("summary"));
        dto.setContent(document.get("content"));
        dto.setTitle(document.get("title"));
        dto.setSrc(document.get("src"));
        dto.setStatus(document.get("status"));
//        dto.setTag(Arrays.asList(document.get("tag").split(",")));

        return dto;
    }

    private void booleanQueryAddNotEmpty(BooleanQuery.Builder builder, String keyName, String keyVal) {
        booleanQueryAddNotEmpty(builder, keyName, keyVal, BooleanClause.Occur.MUST);
    }

    private void booleanQueryAddNotEmpty(BooleanQuery.Builder builder, String keyName, String keyVal, BooleanClause.Occur occur) {
        if (!StringUtils.hasText(keyVal)) {
            return;
        }
        try {
            builder.add(buildQuery(keyName, keyVal), occur);
        } catch (ParseException ex) {
            throw new BizException("booleanQueryAddNotEmpty ParseException", ex);
        }
    }

    private Query buildQuery(String keyName, String keyVal) throws ParseException {
        return (new QueryParser(keyName, new IKSynonymAnalyzer())).parse(keyVal);
    }

    @Test
    public void addOrUpdateCheckTest(){
        IndexReader indexReader = null;
        Directory directory = null;
        try {
            directory = FSDirectory.open(Paths.get(path));
            indexReader = DirectoryReader.open(directory);

            System.out.println(indexReader.maxDoc());
            System.out.println(indexReader.numDeletedDocs());
            System.out.println(indexReader.numDocs());



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexReader != null) {
                try {
                    indexReader.close();
                } catch (Exception e) {
                    log.error("关闭indexReader发生异常", e);
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (Exception e) {
                    log.error("模糊搜索发生异常：", e);
                }
            }
        }
    }

    @Test
    public void addIndexOrDbTest() {
        IndexWriter iwriter = null;
        Directory directory = null;

        Article article = new Article();
        article.setId("a001");
        article.setTitle(String.format("测试001,%s", DateUtil.now()));
        article.setSummary(String.format("这是一个测试001,%s", DateUtil.now()));
        article.setContent("测试content");
        article.setTag(Arrays.asList("测试,content".split(",")));
        article.setStatus("03");
        article.setSrc("test");

        try {

            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }

            Analyzer analyzer = new IKSynonymAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            directory = FSDirectory.open(Paths.get(path));
            iwriter = new IndexWriter(directory, config);

            createIndexDb(iwriter, Arrays.asList(article), "add");
        } catch (IOException e) {
            log.error("创建索引发生异常：", e);
        } finally {
            try {
                if (iwriter != null) {
                    iwriter.close();
                }
                if (directory != null) {
                    directory.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建全量索引测试
     */
    @Test
    public void createIndexDbTest() {
        List<Article> list = ArticleDataFactory.buildArticles();
        IndexWriter iwriter = null;
        Directory directory = null;

        try {

            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }

            Analyzer analyzer = new IKSynonymAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            directory = FSDirectory.open(Paths.get(path));
            iwriter = new IndexWriter(directory, config);

            iwriter.deleteAll();

            createIndexDb(iwriter, list, "all");
        } catch (IOException e) {
            log.error("创建索引发生异常：", e);
        } finally {
            try {
                if (iwriter != null) {
                    iwriter.close();
                }
                if (directory != null) {
                    directory.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void createIndexDb(IndexWriter indexWriter, List<Article> list, String fun) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if ("add".equals(fun)) {
            List<Query> querys = list.stream().map(item -> {
                return (new TermQuery(new Term("id", item.getId())));
            }).filter(item -> item != null).collect(Collectors.toList());
            try {
                indexWriter.deleteDocuments(querys.toArray(new Query[querys.size()]));
            } catch (Exception ex) {
                log.error("删除索引文件出错");
            }
        }
        log.info("即将本地索引的数据为:");
        log.info(JSONUtil.toJsonStr(list));

        list.forEach(article -> {
            // 创建Document对象
            Document document = new Document();

            document.add(new TextField("title", article.getTitle(), Field.Store.YES));
            document.add(new TextField("content", article.getContent(), Field.Store.YES));
            document.add(new StringField("id", article.getId(), Field.Store.YES));
            document.add(new TextField("summary", article.getSummary(), Field.Store.YES));
            document.add(new StringField("status", article.getStatus(), Field.Store.YES));
            document.add(new StringField("src", article.getSrc(), Field.Store.YES));
            document.add(new StringField("tag", String.join(",", article.getTag()), Field.Store.YES));

            try {
                indexWriter.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
