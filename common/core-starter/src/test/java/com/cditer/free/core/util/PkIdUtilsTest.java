package com.cditer.free.core.util;


import org.junit.Assert;
import org.junit.Test;

public class PkIdUtilsTest {

    @Test
    public void getId() {
        String id = PkIdUtils.getId();
        Assert.assertNotNull(id);
        Assert.assertTrue(id.length()==36);
    }
}