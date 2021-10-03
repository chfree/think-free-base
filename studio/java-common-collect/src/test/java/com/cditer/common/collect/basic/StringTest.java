package com.cditer.common.collect.basic;

import org.junit.Assert;
import org.junit.Test;

import java.util.IllegalFormatConversionException;

/**
 * String.format 学习https://cloud.tencent.com/developer/article/1607231
 */

public class StringTest {
    @Test
    public void testFormat(){
        Assert.assertEquals("hello cheng", String.format("hello %s", "cheng"));
        Assert.assertEquals("hello c", String.format("hello %c", 'c'));

        Assert.assertThrows(IllegalFormatConversionException.class, ()->String.format("hello %c", "cheng"));

        Assert.assertEquals("result is true", String.format("result is %b", true));
    }
}
