package com.tennetcn.free.core.validator.annotation;

import com.tennetcn.free.core.validator.check.MethodCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-01-14 12:35
 * @comment
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MethodCheckValidator.class})
public @interface MethodCheck {

    String message() default "调用方法校验不通过";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> clazz();

    String method();

    Class<?>[] parameterTypes();
}
