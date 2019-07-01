package com.tennetcn.free.core.utils;

import org.springframework.util.StringUtils;

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
}
