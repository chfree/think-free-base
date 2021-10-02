package com.cditer.free.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootApplication
public class CoreCommandLineAppTest implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CoreCommandLineAppTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("coreCommandLineApp is run");
    }
}
