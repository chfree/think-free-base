package com.cditer.free.quartz.enums;

import com.cditer.free.core.enums.BaseEnum;

public enum DataCleanExecType implements BaseEnum<String> {
    DATA_TABLE("数据表" ,"01"),
    SHELL("Sheel脚本","02"),
    SERVICE("服务","03");

    private final String text;

    private final String value;

    DataCleanExecType(String text,String value) {
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
