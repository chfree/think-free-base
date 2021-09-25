package com.cditer.free.web.demo;

import com.cditer.free.swagger.annotations.EnableThinkSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableThinkSwagger
public class WebDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(WebDemoApp.class,args);
    }
}
