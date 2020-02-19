package com.tennetcn.free.core.util;

import com.tennetcn.free.core.message.common.ClassMetadata;

import java.lang.reflect.Field;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-11-28 21:19
 * @comment
 */

public class ReflectUtils {
    public static ClassMetadata getField(Object object, String fieldName) throws IllegalAccessException {
        if (object == null) {
            return null;
        }
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                ClassMetadata classMetadata = new ClassMetadata();
                classMetadata.setGenericTypeName(field.getGenericType().getTypeName());
                classMetadata.setTypeName(field.getType().getTypeName());

                field.setAccessible(true);
                classMetadata.setValue(field.get(object));

                return classMetadata;
            }
        }
        return null;
    }
}
