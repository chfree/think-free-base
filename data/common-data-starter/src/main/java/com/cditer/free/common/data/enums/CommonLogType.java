package com.cditer.free.common.data.enums;

import com.cditer.free.core.enums.BaseEnum;

public enum CommonLogType implements BaseEnum<String> {

    INFO("信息","info"),
    WARN("警告","warn"),
    DEBUG("调试","debug"),
    ERROR("错误","error");


    private String text;

    private String value;

    CommonLogType(String text, String value) {
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