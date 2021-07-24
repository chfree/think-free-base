package com.cditer.free.core.boot.autoconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@PropertySource("classpath:core-boot.properties")
public class CoreBootConfig {

    /**
     * 雪花算法数据中心位，最大不能超过31（31<= dcId >=0）
     */
    @Value("${think.util.snowflake.data-center-id:1}")
    private int dataCenterId;

    /**
     * 雪花算法机器位，最大不能超过31（31<= mchId >=0）
     */
    @Value("${think.util.snowflake.machine-id:1}")
    private int machineId;

    /**
     * 生成id的模式
     * 支持uuid，snow
     */
    @Value("${think.util.id-mode:uuid}")
    private String idMode;
}
