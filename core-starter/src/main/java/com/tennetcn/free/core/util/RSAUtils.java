package com.tennetcn.free.core.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-12-02 18:19
 * @comment
 */

public class RSAUtils {
    public static final String RSA_ALGORITHM_NOPADDING = "RSA";

    /**
     * 返回一对公钥、私钥key，形如：[pubKey,priKey]
     */
    public static String[] buildPubPriKey(){
        RSA rsa = new RSA(RSA_ALGORITHM_NOPADDING);
        // 获取prikey和pubkey
        return new String[]{ rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64() };
    }

    /**
     * 加密
     */
    public static String encrypt(String data,String publicKeyBase64) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NOPADDING);
        cipher.init(Cipher.ENCRYPT_MODE, SecureUtil.generatePublicKey(RSA_ALGORITHM_NOPADDING, Base64.decode(publicKeyBase64)));

        return Base64.encode(cipher.doFinal(data.getBytes(CharsetUtil.CHARSET_UTF_8))).toString();
    }

    /**
     * 解密
     */
    public static String decrypt(String data,String privateKeyBase64) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        byte[] dataArray = data.getBytes();

        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NOPADDING);
        cipher.init(Cipher.DECRYPT_MODE, SecureUtil.generatePrivateKey(RSA_ALGORITHM_NOPADDING, Base64.decode(privateKeyBase64)));

        return new String(cipher.doFinal(Base64.decode(dataArray)), CharsetUtil.CHARSET_UTF_8);
    }
}
