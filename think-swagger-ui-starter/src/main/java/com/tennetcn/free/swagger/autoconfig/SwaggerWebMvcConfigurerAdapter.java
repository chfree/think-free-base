package com.tennetcn.free.swagger.autoconfig;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-10-05 22:21
 * @comment
 */

@Component
public class SwaggerWebMvcConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thinkswagger/**").addResourceLocations("/WEB-INF/thinkswagger/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
