package com.tennetcn.free.web.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-20 09:16
 * @comment
 */

@Data
@Configurable
public class ThinkWebConfig {
    @Value("${think.cors.enabled:true}")
    private boolean corsEnabled;

    @Value("${think.cors.path:/**}")
    private String corsPath;

    @Value("${think.log.request:true}")
    private boolean logRequest;

    @Value("${think.log.response:true}")
    private boolean logResponse;

    @Value("${server.port:8080}")
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;
}
