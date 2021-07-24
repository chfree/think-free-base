package com.cditer.free.core.validator.annotation;

import com.cditer.free.core.validator.check.MaxBytesLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MaxBytesLengthValidator.class}
)
public @interface MaxBytesLength {
    String message() default "字符长度超长";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}