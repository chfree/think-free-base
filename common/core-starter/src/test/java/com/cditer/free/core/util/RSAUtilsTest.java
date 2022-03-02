package com.cditer.free.core.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RSAUtilsTest {

    @Test
    public void buildPubPriKey() {
        String[] pubPriKeys = RSAUtils.buildPubPriKey();

        Assert.assertTrue(pubPriKeys.length==2);
        Assert.assertNotNull(pubPriKeys[0]);
        Assert.assertNotNull(pubPriKeys[1]);
    }

    @Test
    public void encrypt() {
    }

    @Test
    public void decrypt() {
    }
}