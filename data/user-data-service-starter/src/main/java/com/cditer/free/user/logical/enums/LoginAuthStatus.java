package com.cditer.free.user.logical.enums;

import com.cditer.free.core.enums.BaseEnum;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-16 09:42
 * @comment
 */

public enum LoginAuthStatus implements BaseEnum<String> {
    VALID("有效","valid"),
    INVALID("无效","invalid");


    private String text;

    private String value;

    LoginAuthStatus(String text, String value){
        this.text = text;
        this.value = value;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getValue() {
        return value;
    }
}
