package com.cditer.free.core.autoconfig;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@EnableCaching
@ImportResource(locations={"classpath:core-spring-boot-config.xml"})
public class CoreAutoConfiguration {
}
