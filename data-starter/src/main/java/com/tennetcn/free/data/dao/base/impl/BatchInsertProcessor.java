package com.tennetcn.free.data.dao.base.impl;

import com.tennetcn.free.data.dao.base.IBatchInsertProcessor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-08-20 08:14
 * @comment
 */

@Component
public class BatchInsertProcessor implements IBatchInsertProcessor {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int insertListBatch(String sqlId, List data) {
        return insertListBatch(sqlId,data,64);
    }

    @Override
    public int insertListBatch(String sqlId, List data, int batchSize) {
        Assert.isTrue(batchSize > 0,"批量插入规模数量必须大于0");
        if(data==null||data.isEmpty()){
            return 0;
        }
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            final int size = data.size();
            for (int i = 0; i < size; i++) {
                session.insert(sqlId, data.get(i));
                if ((i > 0 && (i % batchSize == 0))
                        || i == (size - 1)) {
                    session.commit();
                    session.clearCache();
                }
            }
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            session.close();
        }
        return data.size();
    }

    @Override
    public int updateListBatch(String sqlID, List lstData){
        if(lstData==null||lstData.isEmpty()){
            return 0;
        }
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            int len =  lstData.size() ;
            for (int i = 0; i < len; ++i) {
                session.update(sqlID, lstData.get(i));
            }
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            session.close();
        }
        return lstData.size();
    }
}
