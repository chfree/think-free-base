package com.tennetcn.free.activiti.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-09 22:58
 * @comment
 */

@SpringBootApplication
public class ActivitiAppRun implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiAppRun.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
