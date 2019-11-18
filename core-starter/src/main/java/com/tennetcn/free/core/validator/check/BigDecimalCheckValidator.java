package com.tennetcn.free.core.validator.check;

import com.tennetcn.free.core.validator.annotation.BigDecimalCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-11-18 12:24
 * @comment
 */

public class BigDecimalCheckValidator implements ConstraintValidator<BigDecimalCheck, BigDecimal> {
    //小数点后面的位数
    private int pointNumber;
    //判断数字是不是必须是正数
    private boolean positive;
    @Override
    public void initialize(BigDecimalCheck constraintAnnotation) {
        pointNumber=constraintAnnotation.pointNumber();
        positive=constraintAnnotation.positive();
    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        if (bigDecimal==null) return false;
        int scale=bigDecimal.scale();
        if (scale!=pointNumber) return false;
        if (positive&&bigDecimal.signum()!=1) return false;
        return true;
    }
}
