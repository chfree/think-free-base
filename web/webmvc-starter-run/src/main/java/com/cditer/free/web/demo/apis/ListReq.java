package com.cditer.free.web.demo.apis;

import lombok.Data;

import java.util.List;

@Data
public class ListReq {
    private String name;

    private List<String> userIds;
}
