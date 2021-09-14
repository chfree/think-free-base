package com.cditer.free.core.validator.check;

import com.cditer.free.core.validator.annotation.MaxBytesLength;
import com.cditer.free.core.util.StringHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-11-18 12:27
 * @comment
 */

public class MaxBytesLengthValidator implements ConstraintValidator<MaxBytesLength,String> {
    private int value;
    @Override
    public void initialize(MaxBytesLength constraintAnnotation) {
        value=constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringHelper.getStringLength(s,"utf-8")<=value;
    }
}
