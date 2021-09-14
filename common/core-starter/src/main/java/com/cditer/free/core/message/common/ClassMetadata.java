package com.cditer.free.core.message.common;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-11-28 21:23
 * @comment
 */

@Data
public class ClassMetadata {
    private String typeName;

    private String genericTypeName;

    private Object value;
}
