package com.cditer.free.core.validator.check;

import cn.hutool.core.util.ObjectUtil;
import com.cditer.free.core.validator.annotation.AtLeastOneNotEmpty;
import com.cditer.free.core.message.common.ClassMetadata;
import com.cditer.free.core.util.ReflectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-11-28 18:22
 * @comment
 */

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {
    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotEmpty atLeastOneNotEmpty) {
        this.fields = atLeastOneNotEmpty.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return true;
        }
        try {
            for (String fieldName : fields) {
                ClassMetadata classMetadata = ReflectUtils.getField(object, fieldName);

                if(List.class.getName().equals(classMetadata.getTypeName())){
                    return ObjectUtil.isNotEmpty((List)classMetadata.getValue());
                }

                if (classMetadata.getValue() != null && !"".equals(classMetadata.getValue())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}