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

    /**
     * 跨域中是否允许浏览器读取response内容
     */
    @Value("${think.cors.credentials:true}")
    private boolean corsCredentials;

    /**
     * 允许服务器标示除了它自己以外的其它origin（域，协议和端口）
     */
    @Value("${think.cors.allowed-origin:*}")
    private String corsAllowedOrigin;

    /**
     * 允许哪些请求头
     */
    @Value("${think.cors.allowed-header:*}")
    private String corsAllowedHeader;

    /**
     * 允许哪些方法（POST, GET, PUT, DELETE, OPTIONS）
     */
    @Value("${think.cors.allowed-method:*}")
    private String corsAllowedMethod;

    /**
     * 允许哪些响应头
     */
    @Value("${think.cors.exposed-header:*}")
    private String corsExposedHeader;

    @Value("${think.log.request:true}")
    private boolean logRequest;

    @Value("${think.log.response:true}")
    private boolean logResponse;

    @Value("${server.port:8080}")
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;
}
