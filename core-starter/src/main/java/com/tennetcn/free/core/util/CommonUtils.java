package com.tennetcn.free.core.util;

import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String repairZero(int num,int length){
        String str = String.format("%0"+length+"d", num);
        return str;
    }

    public static String repairZero(String num,int length){
        return repairZero(Integer.parseInt(num),length);
    }

    public static String toPascal(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    public static int getCharsCount(String pattern,String chars){
        int oldLen=pattern.length();
        int newLen= pattern.replace(chars,"").length();
        return oldLen-newLen;
    }

    public static int getNumber(String content) {
        String result=getStringNumber(content);
        if(StringUtils.isEmpty(result)){
            return 0;
        }
        return Integer.parseInt(result);
    }

    public static String getStringNumber(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static boolean isInterface(Class<?> c,Class<?> clazz) {
        String szInterface=clazz.getName();
        return isInterface(c,szInterface);
    }

    public static boolean isInterface(Class<?> c,String szInterface) {
        Class<?>[] face = c.getInterfaces();
        for (int i = 0, j = face.length; i < j; i++) {
            if (face[i].getName().equals(szInterface)) {
                return true;
            } else {
                Class<?>[] face1 = face[i].getInterfaces();
                for (int x = 0; x < face1.length; x++) {
                    if (face1[x].getName().equals(szInterface)) {
                        return true;
                    } else if (isInterface(face1[x], szInterface)) {
                        return true;
                    }
                }
            }
        }
        if (null != c.getSuperclass()) {
            return isInterface(c.getSuperclass(), szInterface);
        }
        return false;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[-]{0,1}[0-9]*");
        Matcher isNum = pattern.matcher(str);

        return isNum.matches();
    }
}
