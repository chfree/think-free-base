package com.tennetcn.free.core.boot.autoconfig;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@EnableCaching
@ImportResource(locations={"core-spring-boot-config.xml"})
public class CoreAutoConfiguration {
}
