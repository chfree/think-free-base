package com.cditer.free.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataTestApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("DataTestApp is runing");
    }
}
