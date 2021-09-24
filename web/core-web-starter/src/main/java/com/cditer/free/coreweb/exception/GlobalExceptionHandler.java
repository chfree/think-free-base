package com.cditer.free.coreweb.exception;

import cn.hutool.json.JSONUtil;
import com.cditer.free.core.exception.BizException;
import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.core.util.StringHelper;
import com.cditer.free.core.validator.annotation.AtLeastOneNotEmpty;
import com.cditer.free.coreweb.message.WebResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
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
        BaseResponse response = new BaseResponse();
        response.setStatus(WebResponseStatus.DATA_ERROR);

        response.setMessage(e.getMessage());
        fillTraceInfo(request,response,e);

        return response;
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public BaseResponse bizExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        BaseResponse response = new BaseResponse();

        BizException bizException= (BizException)e;
        response.setStatus(bizException.getCode());
        response.setMessage(bizException.getMessage());

        fillTraceInfo(request,response,e);

        return response;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public BaseResponse validationExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        BaseResponse response = new BaseResponse();

        ValidationException validationException= (ValidationException)e;

        if(validationException.getCause() instanceof BizException){
            BizException bizException = (BizException)validationException.getCause();
            response.setStatus(bizException.getCode());
            response.setMessage(bizException.getMessage());
        }else{
            response.setStatus(WebResponseStatus.DATA_ERROR);
            response.setMessage(validationException.getMessage());
        }

        fillTraceInfo(request,response,e);

        return response;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public BaseResponse nullPointerExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
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
        response.setMessage(out);

        fillTraceInfo(request,response,e);

        return response;
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResponse bindExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        BaseResponse response = new BaseResponse();
        BindException bindException = (BindException)e;

        StringBuffer message=new StringBuffer();
        List<BindErrorMessage> allMessage=new ArrayList<BindErrorMessage>();
        List<ObjectError> objectErrors = bindException.getAllErrors();
        for (ObjectError objectError: objectErrors) {
            message.append(objectError.getDefaultMessage()).append(".");
            if(objectError instanceof FieldError){
                FieldError fieldError = (FieldError)objectError;
                allMessage.add(new BindErrorMessage(fieldError.getField(),fieldError.getObjectName(),fieldError.getDefaultMessage()));
            }else{
                String field = getFieldNames(objectError);
                allMessage.add(new BindErrorMessage(field,objectError.getObjectName(),objectError.getDefaultMessage()));
            }
        }
        response.setMessage(message.toString());
        response.setAllMessage(JSONUtil.toJsonStr(allMessage));
        response.setStatus(WebResponseStatus.DATA_NULL_ERROR);

        fillTraceInfo(request,response,e);

        return response;
    }

    private String getFieldNames(ObjectError objectError){
        Object[] objects = objectError.getArguments();

        String atLeastOneNotEmpty= AtLeastOneNotEmpty.class.getSimpleName();
        if(atLeastOneNotEmpty.equals(objectError.getCode())){
            if(objects.length==2){
                String[] fields = (String[])objects[1];
                return StringHelper.join(fields);
            }
        }

        return "";
    }

    private void fillTraceInfo(HttpServletRequest request,BaseResponse response,Exception e){
        String traceId = request.getAttribute("traceId").toString();
        log.error("exceptionHandler:【"+traceId+"】"+e.getMessage(), e);

        response.setTraceId(traceId);
    }
}

@Data
@AllArgsConstructor
class BindErrorMessage{
    private String field;

    private String objectName;

    private String defaultMessage;
}
