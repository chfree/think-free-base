package com.cditer.free.coreweb.test.model;

import com.cditer.free.core.message.web.BaseResponse;
import lombok.Data;

@Data
public class UserModelResp extends BaseResponse {
    private User user;
}
