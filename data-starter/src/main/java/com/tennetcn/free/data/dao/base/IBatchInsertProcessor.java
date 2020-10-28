package com.tennetcn.free.data.dao.base;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 18:06
 * @comment
 */

public interface IBatchInsertProcessor {
    int insertListBatch(String sqlId, List data);

    int insertListBatch(String sqlId, List data, int batchSize);

    int updateListBatch(String sqlID, List lstData);
}
