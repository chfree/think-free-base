package com.tennetcn.free.data.boot.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:data-boot.properties")
public class DataBootConfig {
    public DataBootConfig(){
        System.out.println("DataBootConfig: start");
    }
}
