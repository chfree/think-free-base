package com.tennetcn.free.quartz.enums;

import com.tennetcn.free.core.enums.BaseEnum;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-27 12:04
 * @comment
 */

public enum QuartzTaskConcurrent implements BaseEnum<String> {
    YES("是","y"),
    NO("否","n");

    private String text;

    private String value;

    QuartzTaskConcurrent(String text, String value) {
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
