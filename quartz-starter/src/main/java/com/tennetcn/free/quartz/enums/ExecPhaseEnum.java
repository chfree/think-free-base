package com.tennetcn.free.quartz.enums;

import com.tennetcn.free.core.enums.BaseEnum;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-29 22:37
 * @comment
 */

public enum ExecPhaseEnum implements BaseEnum<String> {

    JOBTOBEEXECUTED("jobToBeExecuted","jobToBeExecuted"),
    JOBWASEXECUTED("jobWasExecuted","jobWasExecuted");


    private String key;

    private String value;

    ExecPhaseEnum(String key, String value) {
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
