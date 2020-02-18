package com.tennetcn.free.core.boot.autoconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@PropertySource("classpath:core-boot.properties")
public class CoreBootConfig {

    @Value("${think.util.snowflake.data-center-id:1}")
    private int dataCenterId;

    @Value("${think.util.snowflake.machine-id:1}")
    private int machineId;
}
