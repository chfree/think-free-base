package com.cditer.free.quartz.enums;

import com.cditer.free.core.enums.BaseEnum;

public enum DataCleanExecCycle implements BaseEnum<String> {
    DAY_END("日终" ,"01"),
    WEEK_END("周末","02"),
    MONTH_END("月末","03"),
    YEAR_END("年末","04");

    private final String text;

    private final String value;

    DataCleanExecCycle(String text, String value) {
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
