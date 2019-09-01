package com.tennetcn.free.web.message;

import com.tennetcn.free.web.webapi.BaseResponse;
import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-29 23:12
 * @comment
 */

@Data
public class BasePagerResp extends BaseResponse {
    private int totalCount;
}
