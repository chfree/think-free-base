package com.cditer.lucene.base;

import cn.hutool.json.JSONUtil;
import com.cditer.lucene.data.ArticleDataFactory;
import com.cditer.lucene.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.wltea.analyzer.lucene.IKSynonymAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FirstExample {

    public void queryArticleTest(){

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
            String path = "E:\\document\\testfile\\lucene\\test\\article";

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

            createIndexDb(iwriter, list);
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


    private void createIndexDb(IndexWriter indexWriter, List<Article> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
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

            try {
                indexWriter.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
