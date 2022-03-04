package com.cditer.free.core.util;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

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
    public void encryptAndDecrypt() {
        String[] pubPriKeys = RSAUtils.buildPubPriKey();
        String pubKey = pubPriKeys[0];
        String priKey = pubPriKeys[1];
        try {
            String data = "chfree";
            String chfree = RSAUtils.encrypt(data, pubKey);
            Assert.assertNotNull(chfree);

            String decrypt = RSAUtils.decrypt(chfree, priKey);
            Assert.assertEquals(data, decrypt);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}