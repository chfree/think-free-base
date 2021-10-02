package com.cditer.free.core.util;

import com.cditer.free.core.message.common.ClassMetadata;
import com.cditer.free.core.util.model.TestUser;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Column;
import java.lang.reflect.Field;

public class ReflectUtilsTest {

    @Test
    public void getField() {
        TestUser testUser = new TestUser();
        testUser.setName("cheng");

        ClassMetadata name = ReflectUtils.getField(testUser, "name");

        Assert.assertEquals(name.getValue(), testUser.getName());
    }

    @Test
    public void getFieldName() {
        String fieldName = ReflectUtils.getFieldName(TestUser::getName);

        Assert.assertEquals(fieldName, "name");
    }

    @Test
    public void testGetField() {
        Field field = ReflectUtils.getField(TestUser::getName);

        Assert.assertEquals(field.getName(), "name");
    }

    @Test
    public void getFieldByAnno() {
        Column column = ReflectUtils.getFieldByAnno(TestUser::getName, Column.class);

        Assert.assertEquals(column.name(), "name");
    }

    @Test
    public void findField() {
        Field field = ReflectUtils.findField(TestUser::getName);

        Assert.assertEquals(field.getName(), "name");
    }
}