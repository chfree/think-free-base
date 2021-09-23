package com.cditer.free.usersever.logical.enums;

import com.cditer.free.core.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-08-12 08:36
 * @comment
 */

public enum LoginStatus implements BaseEnum<String> {

    UNACTIVE("待激活","unActive"),
    NORMAL("正常","normal"),
    LOCK("锁定","lock"),
    FORBIDDEN("禁用","forbidden"),
    DELETE("废除","delete");

    private String text;
    private String value;

    LoginStatus(String text, String value){
        this.text = text;
        this.value = value;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static String convert2Text(String value){
        for (LoginStatus c : LoginStatus.values()) {
            if (StringUtils.equals(c.value, value)) {
                return c.text;
            }
        }
        return "";
    }
}
