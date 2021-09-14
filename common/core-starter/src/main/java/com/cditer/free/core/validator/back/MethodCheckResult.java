package com.cditer.free.core.validator.back;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-01-16 00:04
 * @comment
 */

@Data
public class MethodCheckResult {
    private boolean valid;

    private String message;
}
