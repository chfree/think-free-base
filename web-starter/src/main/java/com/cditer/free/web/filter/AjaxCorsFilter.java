package com.cditer.free.web.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-07-27 16:55
 * @comment
 */
public class AjaxCorsFilter extends CorsFilter implements OrderedFilter {
    private int order = -100;

    /**
     * Constructor accepting a {@link CorsConfigurationSource} used by the filter
     * to find the {@link CorsConfiguration} to use for each incoming request.
     *
     * @param configSource
     * @see UrlBasedCorsConfigurationSource
     */
    public AjaxCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
