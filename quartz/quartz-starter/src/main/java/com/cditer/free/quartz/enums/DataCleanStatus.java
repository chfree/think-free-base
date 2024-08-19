package com.cditer.free.quartz.enums;

import com.cditer.free.core.enums.BaseEnum;

public enum DataCleanStatus implements BaseEnum<String> {

    OPEN("开启", "00"),
    CLOSE("关闭", "01");

    private final String text;

    private final String value;

    DataCleanStatus(String text, String value) {
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
