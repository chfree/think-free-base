package com.cditer.free.quartz.autoconfig;

import org.springframework.context.annotation.ImportResource;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(value = "com.cditer.free.quartz.logical.mapper")
@ImportResource(locations={"classpath*:quartz-spring-boot-config.xml"})
public class QuartzAutoConfiguration {

}
