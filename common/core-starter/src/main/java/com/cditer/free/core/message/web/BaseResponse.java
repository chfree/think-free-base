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
 *
 * 2022-06-26
 * 将陆续去掉map类型的arguments大包裹任意形式的数据返回
 * 推荐使用泛型参数
 */

@Data
public class BaseResponse<T> {

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

    /**
     * 数据
     */
    private T data;

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

    @Deprecated
    public void put(String key,Object value){
        if(arguments == null){
            arguments = new HashMap<String, Object>();
        }
        arguments.put(key, value);
    }

    @Deprecated
    public void putResult(Object value){
        if(arguments == null){
            arguments = new HashMap<String, Object>();
        }
        arguments.put(defaultResultKey, value);
    }

    @Deprecated
    public void putErrorMessage(String message){
        this.status = ResponseStatus.SERVER_ERROR;
        setMessage(message);
    }

    @Deprecated
    public Object get(String key){
        if(arguments == null){
            return null;
        }
        return arguments.get(key);
    }

    @Deprecated
    @JsonAnyGetter
    public Map<String,Object> getArguments(){
        return this.arguments;
    }

    public static <T> BaseResponse<T> success(){
        BaseResponse<T> resp = new BaseResponse<>();
        resp.setStatus(ResponseStatus.SUCCESS);

        return resp;
    }

    public static <T> BaseResponse<T> success(T t){
        BaseResponse<T> resp = BaseResponse.success();
        resp.setData(t);

        return resp;
    }

    public static <T> BaseResponse<T> error(){
        return BaseResponse.error(ResponseStatus.SERVER_ERROR);
    }

    public static <T> BaseResponse<T> error(int status){
        BaseResponse<T> resp = new BaseResponse<>();
        resp.setStatus(status);

        return resp;
    }

    public static <T> BaseResponse<T> error(int status, String message){
        BaseResponse<T> resp = BaseResponse.error(status);
        resp.setMessage(message);

        return resp;
    }

    public static <T> BaseResponse<T> error(String message){
        BaseResponse<T> resp = BaseResponse.error(ResponseStatus.SERVER_ERROR);
        resp.setMessage(message);

        return resp;
    }

    public static <T> BaseResponse<T> error(T t){
        BaseResponse<T> resp = BaseResponse.error(ResponseStatus.SERVER_ERROR);
        resp.setData(t);

        return resp;
    }
}
