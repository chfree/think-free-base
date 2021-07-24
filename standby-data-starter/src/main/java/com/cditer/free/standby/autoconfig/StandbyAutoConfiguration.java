package com.cditer.free.standby.autoconfig;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations={"standby-spring-boot-config.xml"})
public class StandbyAutoConfiguration {
}
