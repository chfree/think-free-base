package com.tennetcn.free.web.webapi;

import com.tennetcn.free.web.message.WebResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-14 23:07
 * @comment
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse {
    /**
     * 状态
     */
    @NonNull
    private String status;

    /**
     * 消息
     */
    private String message;

    /**
     * 跟踪号
     */
    private String traceId;

    private Map<String,Object> arguments=new HashMap<String, Object>();

    public BaseResponse(){
        this.status = WebResponseStatus.SUCCESS;
    }

    public void put(String key,Object value){
        arguments.put(key, value);
    }

    public Object get(String key){
        return arguments.get(key);
    }
}
