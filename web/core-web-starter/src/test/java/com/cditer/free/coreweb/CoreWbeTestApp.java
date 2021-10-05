package com.cditer.free.coreweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-10 12:14
 * @comment
 */

@SpringBootApplication
public class CoreWbeTestApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CoreWbeTestApp.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
