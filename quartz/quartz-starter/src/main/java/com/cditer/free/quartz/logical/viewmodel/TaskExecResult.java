package com.cditer.free.quartz.logical.viewmodel;

import lombok.Data;

/**
 * @author C.H
 * @email chfree365@qq.com
 * @createtime 2024/7/17 22:54
 * @comment
 */

@Data
public class TaskExecResult {
    /**
     * 日志类型
     */
    private String logType;

    /**
     * 执行结果
     */
    private boolean execResult;

    /**
     * 执行消息
     */
    private String execMessage;

    public static TaskExecResult newResult(boolean execResult){
        return TaskExecResult.newResult(execResult, null);
    }

    public static TaskExecResult newResult(boolean execResult,String logType){
        return TaskExecResult.newResult(execResult, logType, null);
    }

    public static TaskExecResult newResult(boolean execResult,String logType,String execMessage){
        TaskExecResult result = new TaskExecResult();

        result.setExecResult(execResult);
        result.setLogType(logType);
        result.setExecMessage(execMessage);

        return result;
    }
}
