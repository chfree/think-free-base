package com.cditer.free.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TestDataApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestDataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("TestDataApp is runing");
    }
}
