package com.tennetcn.free.data.dao.base.interceptor.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-19 13:24
 * @comment
 */

@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MyBatisPluginRegister {
}
