package com.tennetcn.free.quartz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-24 21:25
 * @comment
 */


@EnableScheduling
@SpringBootApplication
@ComponentScan(value = "com.tennetcn.free.quartz.demo")
public class QuartzDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(QuartzDemoApp.class,args);
    }
}
