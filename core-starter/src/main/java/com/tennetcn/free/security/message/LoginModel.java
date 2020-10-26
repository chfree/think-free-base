package com.tennetcn.free.security.message;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-25 00:09
 * @comment
 */

@Data
public class LoginModel {
    private String id;

    private String name;

    private String account;

    private String token;

    /**
     * 当前部门id，考虑后期扩展成一个人多个部门的场景
     */
    private String currentDeptId;

    private String currentDeptName;

    private Map<String,Object> arguments=new HashMap<>();

    public void put(String key,Object object){
        this.arguments.put(key,object);
    }

    public Object get(String key){
        return this.arguments.get(key);
    }

    public String getString(String key){
        return MapUtil.getStr(arguments,key);
    }

    public int getInt(String key){
        return MapUtil.getInt(arguments,key);
    }

    public double getDouble(String key){
        return MapUtil.getDouble(arguments,key);
    }

    public float getFloat(String key){
        return MapUtil.getFloat(arguments,key);
    }

    public char getChar(String key){
        return MapUtil.getChar(arguments,key);
    }

    public long getLong(String key){
        return MapUtil.getLong(arguments,key);
    }

    public Date getDate(String key){
        return MapUtil.getDate(arguments,key);
    }

    public Short getShort(String key){
        return MapUtil.getShort(arguments,key);
    }

    public boolean getBool(String key){
        return MapUtil.getBool(arguments,key);
    }

    @JsonAnyGetter
    public Map<String,Object> getArguments() {
        return this.arguments;
    }
}
