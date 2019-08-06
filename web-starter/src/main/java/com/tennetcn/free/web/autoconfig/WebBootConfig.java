package com.tennetcn.free.web.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web-boot.properties")
public class WebBootConfig {
}
