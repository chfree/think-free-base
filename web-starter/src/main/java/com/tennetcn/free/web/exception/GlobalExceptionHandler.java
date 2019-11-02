package com.tennetcn.free.web.exception;

import cn.hutool.json.JSONUtil;
import com.tennetcn.free.core.exception.BizException;
import com.tennetcn.free.core.message.web.BaseResponse;
import com.tennetcn.free.web.message.WebResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-26 12:10
 * @comment
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        String traceId = request.getAttribute("traceId").toString();
        log.error("exceptionHandler:【"+traceId+"】"+e.getMessage(), e);


        BaseResponse response = new BaseResponse();
        if (e instanceof BizException) {
            BizException bizException= (BizException)e;
            response.setStatus(bizException.getCode());
        }else {
            response.setStatus(WebResponseStatus.DATA_ERROR);
        }

        response.setTraceId(traceId);
        response.setMessage(e.getMessage());

        return response;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public BaseResponse nullPointerExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        String traceId = request.getAttribute("traceId").toString();
        log.error("nullPointerExceptionHandler:【"+traceId+"】"+e.getMessage(), e);

        BaseResponse response = new BaseResponse();
        if (e instanceof BizException) {
            BizException bizException= (BizException)e;
            response.setStatus(bizException.getCode());
        }else {
            response.setStatus(WebResponseStatus.DATA_ERROR);
        }

        String out = "";
        StackTraceElement[] trace = e.getStackTrace();
        out += e.toString() + "\r\n";
        for (StackTraceElement s : trace) {
            out +=  s + "\r\n";
        }
        response.setTraceId(traceId);
        response.setMessage(out);

        return response;
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResponse bindExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        BaseResponse response = new BaseResponse();
        BindException bindException = (BindException)e;

        StringBuffer message=new StringBuffer();
        List<BindErrorMessage> allMessage=new ArrayList<BindErrorMessage>();
        List<FieldError> fieldErrors =  bindException.getFieldErrors();
        for (FieldError fieldError: fieldErrors) {
            message.append(fieldError.getDefaultMessage()).append(".");
            allMessage.add(new BindErrorMessage(fieldError.getField(),fieldError.getObjectName(),fieldError.getDefaultMessage()));
        }
        response.setMessage(message.toString());
        response.setAllMessage(JSONUtil.toJsonStr(allMessage));
        response.setStatus(WebResponseStatus.DATA_NULL_ERROR);

        return response;
    }
}

@Data
@AllArgsConstructor
class BindErrorMessage{
    private String field;

    private String objectName;

    private String defaultMessage;
}
