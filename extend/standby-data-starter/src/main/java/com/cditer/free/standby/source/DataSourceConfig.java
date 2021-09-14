package com.cditer.free.standby.source;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-21 19:14
 * @comment
 */

@Configuration
public class DataSourceConfig {
    @Bean(name = "standbyDS")
    @ConfigurationProperties(prefix = "spring.datasource.standby") // application.properteis中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
}
