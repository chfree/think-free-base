package com.cditer.free.core.enums;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-19 15:37
 * @comment
 */

public enum  IdModeEnum implements BaseEnum<String>{
    UUID("uuid","UUID"),
    SNOW("snow","SNOW");

    private String text;
    private String value;
    IdModeEnum(String text,String value){
        this.text = text;
        this.value = value;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
