package com.cditer.flux.demo.model;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2023-09-13 12:39
 * @comment
 */

@Data
public class User {
    private String userId;

    private String userName;

    public User(){}

    public User(String userId,String userName){
        this.userId= userId;
        this.userName=userName;
    }
}
