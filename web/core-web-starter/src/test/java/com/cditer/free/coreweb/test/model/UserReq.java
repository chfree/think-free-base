package com.cditer.free.coreweb.test.model;

import com.cditer.free.core.message.web.BaseRequest;
import lombok.Data;

@Data
public class UserReq extends BaseRequest {
    private String name;

    private int age;
}
