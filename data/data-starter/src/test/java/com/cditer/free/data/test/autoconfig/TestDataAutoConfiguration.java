package com.cditer.free.data.test.autoconfig;

import org.springframework.context.annotation.ImportResource;

@ImportResource(locations={"data-spring-boot-config.xml"})
public class TestDataAutoConfiguration {
    public TestDataAutoConfiguration(){
        System.out.println("TestDataAutoConfiguration");
    }
	
}
