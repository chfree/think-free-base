package com.cditer.free.core.message.web;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-29 23:12
 * @comment
 */

@Data
public class BasePagerResp<T> extends BaseResponse<T> {
    private int totalCount;

    public static <T> BasePagerResp<T> success(T t,int totalCount){
        BasePagerResp<T> resp = new BasePagerResp<>();
        resp.setStatus(ResponseStatus.SUCCESS);
        resp.setData(t);
        resp.setTotalCount(totalCount);

        return resp;
    }
}
