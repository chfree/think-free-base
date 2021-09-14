package com.cditer.free.swagger.autoconfig;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chfree
 */

@Component
public class ThinkSwaggerWebMvcConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thinkswagger/**").addResourceLocations("/WEB-INF/thinkswagger/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
