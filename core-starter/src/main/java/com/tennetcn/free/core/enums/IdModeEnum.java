package com.tennetcn.free.core.enums;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-19 15:37
 * @comment
 */

public enum  IdModeEnum implements BaseEnum<String>{
    UUID("uuid","UUID"),
    SNOW("snow","SNOW");

    private String key;
    private String value;
    IdModeEnum(String key,String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
