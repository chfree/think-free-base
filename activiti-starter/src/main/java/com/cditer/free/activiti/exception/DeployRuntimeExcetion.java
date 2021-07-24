package com.cditer.free.activiti.exception;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-11 12:20
 * @comment
 */

public class DeployRuntimeExcetion extends RuntimeException{
    public DeployRuntimeExcetion(){
        super("部署流程时发生异常");
    }

    public DeployRuntimeExcetion(String message){
        super(message);
    }
}
