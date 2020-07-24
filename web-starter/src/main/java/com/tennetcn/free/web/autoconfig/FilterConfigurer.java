package com.tennetcn.free.web.autoconfig;

import com.tennetcn.free.web.configuration.CheckMacConfig;
import com.tennetcn.free.web.configuration.ThinkWebConfig;
import com.tennetcn.free.web.filter.SignatureCheckFilter;
import com.tennetcn.free.web.filter.TraceIdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.annotation.WebFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@PropertySource("classpath:web-boot.properties")
@Import({ThinkWebConfig.class,CheckMacConfig.class})
public class FilterConfigurer implements WebMvcConfigurer {

    @Autowired
    ThinkWebConfig webConfig;

    @Autowired
    CheckMacConfig checkMacConfig;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
        /*
         * 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
         */
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if(webConfig.isCorsEnable()){
            source.registerCorsConfiguration(webConfig.getCorsPath(), buildConfig());
        }
        return new CorsFilter(source);
    }

    @Bean
    public FilterRegistrationBean filterRegistTraceIdFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TraceIdFilter traceIdFilter = new TraceIdFilter();
        registrationBean.setFilter(traceIdFilter);

        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(200);

        return registrationBean;
    }

    @Bean
    public SignatureCheckFilter newSignatureCheckFilter(){
        SignatureCheckFilter signatureCheckFilter = new SignatureCheckFilter();

        return signatureCheckFilter;
    }

    @Bean
    @ConditionalOnProperty(value = "think.checkmac.enabled",havingValue = "true")
    public FilterRegistrationBean filterRegistSignatureCheckFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(newSignatureCheckFilter());
        registrationBean.setOrder(checkMacConfig.getFilterOrder());

        List<String> tempUrlPatterns = (StringUtils.isEmpty(checkMacConfig.getUrlPatterns()))
                ? Arrays.asList(checkMacConfig.getUrlPatterns().split(","))
                :new ArrayList<String>();
        List<String> urlPatterns = Collections.unmodifiableList(tempUrlPatterns);
        registrationBean.setUrlPatterns(urlPatterns);


        return registrationBean;
    }

}
