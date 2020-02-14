package com.tennetcn.free.core.validator.check;

import cn.hutool.core.annotation.AnnotationUtil;
import com.tennetcn.free.core.exception.BizException;
import com.tennetcn.free.core.utils.CommonApplicationContextUtil;
import com.tennetcn.free.core.validator.annotation.MaxBytesLength;
import com.tennetcn.free.core.validator.annotation.MethodCheck;
import com.tennetcn.free.core.validator.back.MethodCheckResult;
import lombok.var;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.*;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-01-14 12:36
 * @comment
 */

public class MethodCheckValidator implements ConstraintValidator<MethodCheck,Object> {

    private MethodCheck methodCheck;

    @Override
    public void initialize(MethodCheck constraintAnnotation) {
        methodCheck=constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        var instance=CommonApplicationContextUtil.getCurrentContext().getBean(methodCheck.clazz());
        try{
            Method method =instance.getClass().getMethod(methodCheck.method(),methodCheck.parameterTypes());

            Object rtnObj = method.invoke(instance,value);
            if(rtnObj instanceof  Boolean){
                return (Boolean) rtnObj;
            }else if(rtnObj instanceof MethodCheckResult){
                MethodCheckResult methodCheckResult = (MethodCheckResult)rtnObj;

                if(!methodCheckResult.isValid()){
                    throw new BizException(methodCheckResult.getMessage());
                }
                return methodCheckResult.isValid();
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex){
            throw new BizException(ex);
        }
        return false;
    }

    /**
     * 强制通过反射修改后，valid的context还是无法拿到修改的值，所以还是放弃了，直接通过异常将message的值传出去
     * @param message
     */
    private void setMessage(String message) {
        try {
            //获取 methodCheck 这个代理实例所持有的 InvocationHandler
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(methodCheck);
            // 获取 AnnotationInvocationHandler 的 memberValues 字段
            Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
            // 因为这个字段事 private final 修饰，所以要打开权限
            declaredField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) declaredField.get(invocationHandler);
            // 修改 value 属性值
            memberValues.put("message", message);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
