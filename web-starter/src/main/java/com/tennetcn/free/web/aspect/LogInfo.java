package com.tennetcn.free.web.aspect;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-12 18:18
 * @comment
 */

@Slf4j
@Aspect
@Component
public class LogInfo {
    public LogInfo() {
        log.info("logInfo is init");
    }
    @Pointcut("execution( public * com.tennetcn..*.apis..*.* (..)) && @within(org.springframework.web.bind.annotation.RequestMapping)")
    public void weblog() {};

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void annotationCheck(){};

    @Before("weblog() && annotationCheck()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("weblog dobefore start");
        try {
            Object[] args = fitlerArgs(joinPoint.getArgs());
            StringBuffer buf = new StringBuffer();
            buf.append("请求入参\r\n");

            String argsJson = JSONUtil.toJsonStr(args);

            buf.append(argsJson);
            log.info(buf.toString());
        }catch(Throwable e) {

        }
    }

    private Object[] fitlerArgs(Object[] args){
        if(args!=null){
            return Arrays.stream(args).filter(obj-> !(obj instanceof MultipartFile)).toArray();
        }else{
            return new Object[0];
        }
    }

    @AfterReturning(returning="ret",pointcut = "weblog() && annotationCheck()")
    public void doAfterReturning(Object ret) throws Throwable {
        try {
            StringBuffer buf = new StringBuffer();
            buf.append("返回数据\r\n");
            buf.append(JSONUtil.toJsonStr(ret));
            log.info(buf.toString());
        } catch (Throwable e) {
        }
    }

}
