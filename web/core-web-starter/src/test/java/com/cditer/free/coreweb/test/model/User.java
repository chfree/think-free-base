package com.cditer.free.coreweb.test.model;

import lombok.Data;

@Data
public class User {
    private String name;

    private int age;

    public Dept dept;
}
