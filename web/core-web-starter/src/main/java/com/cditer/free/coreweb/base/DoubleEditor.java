package com.cditer.free.coreweb.base;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-27 22:40
 * @comment
 */

public class DoubleEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)||"null".equals(text)) {
            setValue(0d);
        } else {
            try {
                setValue(Double.parseDouble(text));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Could not parse double:" + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
