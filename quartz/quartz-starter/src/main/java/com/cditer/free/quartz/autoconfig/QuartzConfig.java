package com.cditer.free.quartz.autoconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author C.H
 * @email chfree365@qq.com
 * @createtime 2024/7/25 10:38
 * @comment
 */

@Data
@Component
@Configurable
public class QuartzConfig {
    @Value("${think.quartz.scope:}")
    private String scope;
}
