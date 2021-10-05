package com.cditer.free.coreweb.test.model;

import com.cditer.free.core.message.web.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class IndexUserListResp extends BaseResponse {
    private List<User> users;
}
