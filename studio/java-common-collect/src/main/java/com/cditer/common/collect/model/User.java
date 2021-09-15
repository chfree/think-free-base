package com.cditer.common.collect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-15 12:14
 * @comment
 */

@Data
@AllArgsConstructor
public class User {
    private String name;

    private String title;

    private int age;

    public User(){}

    public User(String name,int age){
        this.name = name;
        this.age = age;
    }

}
