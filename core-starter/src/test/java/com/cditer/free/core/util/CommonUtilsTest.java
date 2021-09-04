package com.cditer.free.core.util;

import com.cditer.free.core.cache.CachedImpl;
import com.cditer.free.core.cache.ICached;
import com.cditer.free.core.util.CommonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void testTestIsInterface() {
        boolean anInterface = CommonUtils.isInterface(CachedImpl.class, ICached.class.getName());

        Assert.assertTrue(anInterface);
    }

    @Test
    public void testIsNumeric() {
        Assert.assertTrue(CommonUtils.isNumeric("123"));

        Assert.assertFalse(CommonUtils.isNumeric("adc"));

        Assert.assertFalse(CommonUtils.isNumeric("1a2d3c"));
    }

    @Test
    public void testGetTraceId() {
        String traceId = CommonUtils.getTraceId();

        Assert.assertEquals(traceId.length(),30);

        try(MockedStatic<CommonUtils> mockStatic = mockStatic(CommonUtils.class)){
            mockStatic.when(CommonUtils::getHostName).thenReturn("chenghuan");
            // 一旦 Mock 一个类，那么它所有的静态方法都被 Mockito 接管。
            mockStatic.when(CommonUtils::getTraceId).thenCallRealMethod();

            String traceId1 = CommonUtils.getTraceId();
            Assert.assertEquals(traceId1.length(),30);

            Assert.assertTrue(traceId1.contains("cheng"));
            Assert.assertFalse(traceId1.contains("chenghuan"));

            mockStatic.when(CommonUtils::getHostName).thenReturn("ch");
            String traceId2 = CommonUtils.getTraceId();
            Assert.assertEquals(traceId2.length(),30);
            System.out.println(traceId2);
            Assert.assertTrue(traceId2.contains("XXXch"));
        }

    }

    @Test
    public void testGetHostName() {
        String hostName = CommonUtils.getHostName();

        Assert.assertNotNull(hostName);
    }
}