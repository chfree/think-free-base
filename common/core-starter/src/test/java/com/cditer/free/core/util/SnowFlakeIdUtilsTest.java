package com.cditer.free.core.util;

import org.junit.Assert;
import org.junit.Test;

public class SnowFlakeIdUtilsTest {

    @Test
    public void nextId() {
        Long nextId = SnowFlakeIdUtils.nextId();
        Assert.assertNotNull(nextId);
    }
}