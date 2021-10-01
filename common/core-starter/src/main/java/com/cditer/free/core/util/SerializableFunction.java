package com.cditer.free.core.util;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-10-01 09:15
 * @comment
 */

@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {

}
