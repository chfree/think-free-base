package com.cditer.free.core.util;

import com.cditer.free.core.cache.CachedImpl;
import com.cditer.free.core.cache.ICached;
import com.cditer.free.core.util.CommonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

public class CommonUtilsTest{
    @Test
    public void testGetUUID(){
        String uuid = CommonUtils.getUUID();
        Assert.assertNotNull(uuid);
        Assert.assertTrue(uuid.length()==36);
    }

    @Test
    public void testRepairZero(){
        String result1 = CommonUtils.repairZero(1, 5);

        Assert.assertEquals(result1.length(), 5);
        Assert.assertEquals(result1, "00001");

        String result2 = CommonUtils.repairZero(999999, 5);
        Assert.assertEquals(result2, "999999");

        Assert.assertThrows(NumberFormatException.class,() -> CommonUtils.repairZero("xxx", 8));

    }

    @Test
    public void testToPascal() {
        String chfree = CommonUtils.toPascal("chfree");
        Assert.assertEquals(chfree, "Chfree");
    }

    @Test
    public void testGetCharsCount() {
        int charsCount = CommonUtils.getCharsCount("abcdefghidkabc", "a");
        Assert.assertEquals(charsCount, 2);
    }

    @Test
    public void testGetNumber() {
        int abc = CommonUtils.getNumber("ABC");
        Assert.assertEquals(0, abc);

        Assert.assertEquals(CommonUtils.getNumber("123"), 123);
    }

    @Test
    public void testGetStringNumber() {
        String abc = CommonUtils.getStringNumber("ABC");
        Assert.assertFalse(StringUtils.hasLength(abc));

        Assert.assertEquals(CommonUtils.getStringNumber("123"), "123");
        Assert.assertEquals(CommonUtils.getStringNumber("9999999999999999999"), "9999999999999999999");
    }

    @Test
    public void testIsInterface() {
        boolean anInterface = CommonUtils.isInterface(CachedImpl.class, ICached.class);

        Assert.assertTrue(anInterface);
    }

    public void testTestIsInterface() {
    }

    public void testIsNumeric() {
    }

    public void testGetTraceId() {
    }

    public void testGetHostName() {
    }
}