package com.cditer.free.coreweb.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-25 09:29
 * @comment
 */

@Configuration
@PropertySource("classpath:web-boot.properties")
@ImportResource(locations={"classpath:core-web-boot-config.xml"})
public class CoreWebAutoConfiguration {
}
