package com.tennetcn.free.core.message.web;

import com.tennetcn.free.core.message.PagerModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-29 23:10
 * @comment
 */

@Data
public class BasePagerReq extends BaseRequest {
    @NotNull(message = "分页信息不能为空")
    private PagerModel pager;
}
