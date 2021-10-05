package com.cditer.free.coreweb.http.inceptor;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.coreweb.configuration.ThinkWebConfig;
import com.cditer.free.coreweb.exception.ResponseException;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import com.cditer.free.coreweb.http.handle.IResponseEntityResolveInceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-14 12:18
 * @comment
 */

@Component
public class ResponseEntityDefaultResolveInceptor implements IResponseEntityResolveInceptor {

    @Autowired
    ThinkWebConfig thinkWebConfig;


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public <T> void resovle(ResponseEntityEx<T> exchangeEx) {
        if(!thinkWebConfig.isOpenDefaultResolveEntity()){
            return;
        }

        if(HttpStatus.OK.value() != exchangeEx.getStatusCodeValue()){
            return;
        }
        if(exchangeEx.getBody() instanceof BaseResponse){
            BaseResponse resp = (BaseResponse)exchangeEx.getBody();
            if(resp.getStatus()!=HttpStatus.OK.value()){
                throw new ResponseException(resp.getStatus(), resp.getMessage());
            }
        }else if(exchangeEx.getBody() instanceof String){
            if(!JSONUtil.isJson(exchangeEx.getBody().toString())){
                return;
            }
            JSONObject jsonObject = JSONUtil.parseObj(exchangeEx.getBody());
            Integer status = jsonObject.getInt("status", HttpStatus.OK.value());

            if(status!=HttpStatus.OK.value()){
                String message = jsonObject.getStr("message", "请求业务返回异常");
                throw new ResponseException(status, message);
            }
        }
    }
}
