package com.cditer.free.core.message.web;

import com.cditer.free.core.message.data.PagerModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-29 23:10
 * @comment
 */

@Data
public class BasePagerReq<T> extends BaseRequest<T> {
    @NotNull(message = "分页信息不能为空")
    private PagerModel pager;
}
