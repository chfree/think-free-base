package com.cditer.common.collect.basic;

import org.junit.Assert;
import org.junit.Test;

public class IntegerTest {

    @Test
    public void testNull(){
        Integer nullInt = null;
        Assert.assertNotNull(String.valueOf(nullInt));

        Integer a = 12;
        Assert.assertEquals("12", String.valueOf(a));
        Assert.assertEquals("12", String.valueOf(a.intValue()));
    }
}
