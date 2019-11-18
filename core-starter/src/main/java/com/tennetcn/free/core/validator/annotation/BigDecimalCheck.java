package com.tennetcn.free.core.validator.annotation;

import com.tennetcn.free.core.validator.check.BigDecimalCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {BigDecimalCheckValidator.class}
)
public @interface BigDecimalCheck {
    String message() default "数字格式不符合规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int pointNumber();
    boolean positive();
}
