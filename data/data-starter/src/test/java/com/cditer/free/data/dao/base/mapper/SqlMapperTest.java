package com.cditer.free.data.dao.base.mapper;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.utils.Pager2RowBounds;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Assert;
import org.junit.Test;

public class SqlMapperTest {
    @Test
    public void rowBound2LimitTest(){
        SqlMapper sqlMapper = new SqlMapper(new DefaultSqlSession(new Configuration(), null));
        String sql = sqlMapper.rowBound2Limit("select * from user", Pager2RowBounds.getRowBounds(new PagerModel(10, 2)));
        Assert.assertEquals("select * from user limit 10,10", sql);

        String sql1 = sqlMapper.rowBound2Limit("select * from user for update", Pager2RowBounds.getRowBounds(new PagerModel(10, 2)));
        Assert.assertEquals("select * from user limit 10,10 for update", sql1);
    }
}