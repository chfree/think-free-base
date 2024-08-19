package com.cditer.free.quartz.enums;

import com.cditer.free.core.enums.BaseEnum;

public enum DataCleanExecStatus  implements BaseEnum<String> {

    WAIT_EXEC("待执行", "00"),
    EXEC_ING("执行中", "01"),
    EXEC_SUCCESS("执行成功", "02"),
    EXEC_ERROR("执行失败", "03");

    private final String text;

    private final String value;

    DataCleanExecStatus(String text, String value) {
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
