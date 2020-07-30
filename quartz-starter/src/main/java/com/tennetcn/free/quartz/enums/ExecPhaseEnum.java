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


    private String text;

    private String value;

    ExecPhaseEnum(String text, String value) {
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
