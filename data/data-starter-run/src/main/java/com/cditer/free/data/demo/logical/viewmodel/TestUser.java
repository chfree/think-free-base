package com.cditer.free.data.demo.logical.viewmodel;

import com.cditer.free.data.demo.logical.model.LoginUser;
import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-10 09:34
 * @comment
 */

@Data
public class TestUser extends LoginUser {
    private String test;

    private String testName;
}
