package com.tennetcn.free.core.util;

import com.tennetcn.free.core.exception.BizException;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class StringHelper {
    public static String join(String[] strs){
        return join(strs,",");
    }
    public static String join(String[] strs,String c){
        if(strs==null||strs.length<=0){
            return "";
        }
        return join(Arrays.asList(strs),c);
    }

    public static String join(List<String> strs){
        return join(strs,",");
    }

    public static String join(List<String> strs, String c){
        if(strs==null||strs.size()<=0){
            return "";
        }
        String result="";
        for (String str : strs) {
            result+=str+c;
        }
        return result.substring(0, result.length()-1);
    }

    public static String fileExt(String fileName){
        if(StringUtils.isEmpty(fileName)||fileName.lastIndexOf(".")<=0){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
    }

    public static int getStringLength(String str,String encoding) {
        if(StringUtils.isEmpty(str)) {
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
