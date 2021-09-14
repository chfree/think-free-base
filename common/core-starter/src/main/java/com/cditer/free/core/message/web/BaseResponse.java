package com.cditer.free.core.message.web;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-14 23:07
 * @comment
 * 关于message和allMessge的设计：
 * message用于直接返回给用户的提示信息，allMessage则更具体；
 * 比如验证用户名和密码不为空，提示可以是 用户名不能为空，
 * 但是前端需要统一进行处理的时候，用户名不能为空对应界面只能写死
 * 此时的allMessage就可以是: {field: 'username',message:'用户名不能为空'}
 */

@Data
@Builder
@AllArgsConstructor
public class BaseResponse {

    public static String defaultResultKey = "result";

    /**
     * 状态
     */
    private int status;

    /**
     * 消息，给用户查看
     */
    private String message;

    /**
     * 完整消息，给业务判断更多的逻辑信息
     */
    private String allMessage;

    /**
     * 跟踪号
     */
    private String traceId;

    private Map<String,Object> arguments= null;

    public BaseResponse(){
        this.status = ResponseStatus.SUCCESS;
    }

    public BaseResponse(int status){
        this.status = status;
    }

    public BaseResponse(int status,String message){
        this.message = message;
        this.status = status;
    }

    public void put(String key,Object value){
        if(arguments == null){
            arguments = new HashMap<String, Object>();
        }
        arguments.put(key, value);
    }

    public void putResult(Object value){
        if(arguments == null){
            arguments = new HashMap<String, Object>();
        }
        arguments.put(defaultResultKey, value);
    }

    public void putErrorMessage(String message){
        this.status = ResponseStatus.SERVER_ERROR;
        setMessage(message);
    }

    public Object get(String key){
        if(arguments == null){
            return null;
        }
        return arguments.get(key);
    }

    @JsonAnyGetter
    public Map<String,Object> getArguments(){
        return this.arguments;
    }
}
