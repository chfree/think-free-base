package com.cditer.free.coreweb.test.model;

import com.cditer.free.core.message.web.BaseResponse;
import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-16 12:30
 * @comment
 */

@Data
public class UserResp extends BaseResponse {
    private String name;

    private String age;

    private Dept dept;
}
