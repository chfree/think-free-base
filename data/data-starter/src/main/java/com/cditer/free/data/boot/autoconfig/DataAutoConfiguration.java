package com.cditer.free.data.boot.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:data-spring-boot-config.xml"})
public class DataAutoConfiguration {
	
}
