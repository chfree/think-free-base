package com.tennetcn.free.quartz.enums;

import com.tennetcn.free.core.enums.BaseEnum;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-27 12:04
 * @comment
 */

public enum QuartzTaskStatus implements BaseEnum<String> {
    OPEN("open","开启"),
    CLOSE("close","关闭");

    private String key;

    private String value;

    QuartzTaskStatus(String key,String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
