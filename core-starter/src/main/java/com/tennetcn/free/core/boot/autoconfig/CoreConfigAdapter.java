package com.tennetcn.free.core.boot.autoconfig;

import com.tennetcn.free.core.properties.FreeProperties;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-25 10:00
 * @comment
 */

@Configurable
@EnableConfigurationProperties(FreeProperties.class)
public class CoreConfigAdapter {
}
