package com.tennetcn.free.quartz.enums;

import com.tennetcn.free.core.enums.BaseEnum;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-27 12:04
 * @comment
 */

public enum QuartzTaskStatus implements BaseEnum<String> {
    OPEN("开启" ,"open"),
    CLOSE("关闭","close");

    private String text;

    private String value;

    QuartzTaskStatus(String text,String value) {
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
}
