package com.tennetcn.free.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-25 09:57
 * @comment
 */
@Data
@ConfigurationProperties(prefix = "free")
public class FreeProperties {
    /**
     * cache
     */
    private CacheProperties cache = new CacheProperties();
}
