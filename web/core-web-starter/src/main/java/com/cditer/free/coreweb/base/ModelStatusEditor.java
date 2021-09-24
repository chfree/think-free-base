package com.cditer.free.coreweb.base;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-28 22:40
 * @comment
 */

public class ModelStatusEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)||"null".equals(text)) {
            setValue(null);
        } else {
            try {
                Class clazz=Class.forName("com.cditer.free.core.enums.ModelStatus");
                setValue(Enum.valueOf(clazz,text));
            } catch (ClassNotFoundException ex) {
                throw new IllegalArgumentException("Could not parse modelstatus:" + ex.getMessage(), ex);
            }
        }
    }
}
