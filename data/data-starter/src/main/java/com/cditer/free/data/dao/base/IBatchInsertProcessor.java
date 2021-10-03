package com.cditer.free.data.dao.base;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 18:06
 * @comment 有事物要求的场景慎用
 * 此中方法，都是不受注解事物，都是单独openSession，然后进行commit或rollback
 */

public interface IBatchInsertProcessor {
    int insertListBatch(String sqlId, List data);

    int insertListBatch(String sqlId, List data, int batchSize);

    int updateListBatch(String sqlID, List lstData);
}
