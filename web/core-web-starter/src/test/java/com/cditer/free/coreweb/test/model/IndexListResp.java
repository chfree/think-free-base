package com.cditer.free.coreweb.test.model;

import com.cditer.free.core.message.web.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class IndexListResp extends BaseResponse {
    private List<String> names;
}
