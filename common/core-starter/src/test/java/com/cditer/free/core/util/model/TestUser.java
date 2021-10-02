package com.cditer.free.core.util.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TestUser {

    @Column(name = "name")
    private String name;

    private Integer age;

    private String title;
}
