package com.cditer.common.collect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-15 12:14
 * @comment
 */

@Data
@AllArgsConstructor
public class User {

    @Column(name = "name")
    private String name;

    private String title;

    private int age;

    private String id;

    public User(){}

    public User(String name,int age){
        this.name = name;
        this.age = age;
    }

}
