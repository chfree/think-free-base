package com.cditer.free.core.util;

import com.cditer.free.core.exception.BizException;
import com.cditer.free.core.message.common.ClassMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-11-28 21:19
 * @comment
 */

public class ReflectUtils {
    public static ClassMetadata getField(Object object, String fieldName){
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
                try {
                    classMetadata.setValue(field.get(object));
                } catch (IllegalAccessException e) {
                    throw new BizException(e);
                }

                return classMetadata;
            }
        }
        return null;
    }

    private static Map<SerializableFunction<?, ?>, Field> cache = new ConcurrentHashMap<>();

    public static <T, R> String getFieldName(SerializableFunction<T, R> function) {
        Field field = ReflectUtils.getField(function);
        return field.getName();
    }

    public static <T, R> Field getField(SerializableFunction<T, R> function) {
        return cache.computeIfAbsent(function, ReflectUtils::findField);
    }

    public static <T,R,A extends Annotation> A getFieldByAnno(SerializableFunction<T, R> function, Class<A> type){
        Field field = getField(function);
        return field.getAnnotation(type);
    }

    public static <T, R> Field findField(SerializableFunction<T, R> function) {
        Field field = null;
        String fieldName = null;
        try {
            // 第1步 获取SerializedLambda
            Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(function);
            // 第2步 implMethodName 即为Field对应的Getter方法名
            String implMethodName = serializedLambda.getImplMethodName();
            if (implMethodName.startsWith("get") && implMethodName.length() > 3) {
                fieldName = Introspector.decapitalize(implMethodName.substring(3));

            } else if (implMethodName.startsWith("is") && implMethodName.length() > 2) {
                fieldName = Introspector.decapitalize(implMethodName.substring(2));
            } else if (implMethodName.startsWith("lambda$")) {
                throw new IllegalArgumentException("SerializableFunction不能传递lambda表达式,只能使用方法引用");

            } else {
                throw new IllegalArgumentException(implMethodName + "不是Getter方法引用");
            }
            // 第3步 获取的Class是字符串，并且包名是“/”分割，需要替换成“.”，才能获取到对应的Class对象
            String declaredClass = serializedLambda.getImplClass().replace("/", ".");
            Class<?> aClass = Class.forName(declaredClass, false, ClassUtils.getDefaultClassLoader());

            // 第4步  Spring 中的反射工具类获取Class中定义的Field
            field = ReflectionUtils.findField(aClass, fieldName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 第5步 如果没有找到对应的字段应该抛出异常
        if (field != null) {
            return field;
        }
        throw new NoSuchFieldError(fieldName);
    }
}
