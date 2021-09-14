package com.cditer.free.data.utils;

import com.cditer.free.core.message.data.PagerModel;
import org.apache.ibatis.session.RowBounds;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-01 14:04
 * @comment
 */

public class Pager2RowBounds {
    public static RowBounds getRowBounds(PagerModel pagerModel){
        return new RowBounds((pagerModel.getPageIndex()-1)*pagerModel.getPageSize(),pagerModel.getPageSize());
    }
}
