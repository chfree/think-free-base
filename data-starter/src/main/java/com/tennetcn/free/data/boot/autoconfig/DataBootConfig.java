package com.tennetcn.free.data.boot.autoconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:data-boot.properties")
public class DataBootConfig {
    /**
     * sql语句执行时长超过时间 默认1000ms
     */
    @Value("${think.data.sql.exec-max-time:1000}")
    private long sqlExecMaxTime;

    @Value("${think.data.sql.print:true}")
    private boolean printSql;
}
