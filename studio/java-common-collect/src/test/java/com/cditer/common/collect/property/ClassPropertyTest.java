package com.cditer.common.collect.property;

import cn.hutool.core.util.ReflectUtil;
import com.cditer.common.collect.model.User;
import com.cditer.free.core.util.ReflectUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;


/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-10-01 09:00
 * @comment
 */

public class ClassPropertyTest {

    @Test
    public void testGetPropertyName(){
        String fieldName = ReflectUtils.getFieldName(User::getName);
        Assert.assertEquals(fieldName, "name");

        Column fieldByAnno = ReflectUtils.getFieldByAnno(User::getName, Column.class);
        Assert.assertEquals("name", fieldByAnno.name());
    }
}
