package com.cditer.free.web.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-07-24 15:17
 * @comment
 */

@Data
@Configurable
public class CheckMacConfig {
    /**
     * 是否启动mac校验
     */
    @Value("${think.checkmac.enabled:false}")
    private boolean enabled;

    /**
     * 盐值
     */
    @Value("${think.checkmac.slat:}")
    private String salt;

    /**
     * 请求头字段名
     */
    @Value("${think.checkmac.header-field-name:signature}")
    private String headerFieldName;

    /**
     * timestamp时的请求头名称
     */
    @Value("${think.checkmac.timestamp-field-name:x-ts}")
    private String timestampFieldName;

    /**
     * timestamp时的请求超时容差时间
     */
    @Value("${think.checkmac.time-threshold:300}")
    private long timeThreshold;

    /**
     * 校验方式：mac、timestamp
     */
    @Value("${think.checkmac.strategy:mac}")
    private String strategy;

    /**
     * SignatureCheckFilter拦截匹配的名单
     */
    @Value("${think.checkmac.url-patterns:/api/*}")
    private String urlPatterns;

    /**
     * filter order
     */
    @Value("${think.checkmac.filter-order:100000000}")
    private int filterOrder;

    /**
     * 在拦截匹配中，允许一些名单
     */
    @Value("${think.checkmac.allowlist:/v2/api-docs}")
    private String allowlist;

}
