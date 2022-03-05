package com.cditer.free.core.util;

import com.cditer.free.core.exception.BizException;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class StringHelper {
    public static String fileExt(String fileName){
        if(StringUtils.isEmpty(fileName)||fileName.lastIndexOf(".")<=0){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
    }

    public static int getStringLength(String str,String encoding) {
        if(!StringUtils.hasText(str)) {
            return 0;
        } else {
            try {
                return str.getBytes(encoding).length;
            }catch (UnsupportedEncodingException ex){
                throw new BizException("根据编码"+encoding+"获取字符长度异常");
            }
        }
    }
}
