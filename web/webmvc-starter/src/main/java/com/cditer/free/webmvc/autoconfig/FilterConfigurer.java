package com.cditer.free.webmvc.autoconfig;

import com.cditer.free.coreweb.configuration.CheckMacConfig;
import com.cditer.free.coreweb.configuration.ThinkWebConfig;
import com.cditer.free.webmvc.filter.AjaxCorsFilter;
import com.cditer.free.webmvc.filter.SignatureCheckFilter;
import com.cditer.free.webmvc.filter.TraceIdFilter;
import com.cditer.free.webmvc.filter.checkhelper.ICheckHelper;
import com.cditer.free.webmvc.filter.checkhelper.MacCheckHelper;
import com.cditer.free.webmvc.filter.checkhelper.TimestampCheckHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
@Import({ThinkWebConfig.class, CheckMacConfig.class})
@Configuration
public class FilterConfigurer implements WebMvcConfigurer {

    @Autowired
    ThinkWebConfig webConfig;

    @Autowired
    CheckMacConfig checkMacConfig;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        /*
         * 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
         */
        corsConfiguration.setAllowCredentials(webConfig.isCorsCredentials());
        if(webConfig.isCorsCredentials()) {
            corsConfiguration.addAllowedOriginPattern(webConfig.getCorsAllowedOriginPattern());
        }else{
            corsConfiguration.addAllowedOrigin(webConfig.getCorsAllowedOrigin());
        }
        corsConfiguration.addAllowedHeader(webConfig.getCorsAllowedHeader());
        corsConfiguration.addAllowedMethod(webConfig.getCorsAllowedMethod());
        corsConfiguration.addExposedHeader(webConfig.getCorsExposedHeader());
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public AjaxCorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if(webConfig.isCorsEnabled()){
            log.info("cors enabled is true");
            source.registerCorsConfiguration(webConfig.getCorsPath(), buildConfig());
        }
        return new AjaxCorsFilter(source);
    }

    @Bean
    public FilterRegistrationBean filterRegistTraceIdFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TraceIdFilter traceIdFilter = new TraceIdFilter();
        registrationBean.setFilter(traceIdFilter);

        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

    @Autowired
    MacCheckHelper macCheckHelper;

    @Autowired
    TimestampCheckHelper timestampCheckHelper;

    private SignatureCheckFilter newSignatureCheckFilter(){
        ICheckHelper helper = null;
        if(checkMacConfig.getStrategy() == null || checkMacConfig.getStrategy().equalsIgnoreCase(SignatureCheckFilter.CHECK_STRATEGY_MAC)){
            helper = macCheckHelper;
        }else if(checkMacConfig.getStrategy().equalsIgnoreCase(SignatureCheckFilter.CHECK_STRATEGY_TIMESTAMP)){
            helper = timestampCheckHelper;
        }else{
            throw new IllegalArgumentException("Input Strategy is Error! input value is " + checkMacConfig.getStrategy() + "must is " + SignatureCheckFilter.CHECK_STRATEGY_MAC + " or " + SignatureCheckFilter.CHECK_STRATEGY_TIMESTAMP);
        }
        return new SignatureCheckFilter(helper,checkMacConfig);
    }

    @Bean
    @ConditionalOnProperty(value = "think.checkmac.enabled",havingValue = "true")
    public FilterRegistrationBean<SignatureCheckFilter> filterRegistSignatureCheckFilter(){
        FilterRegistrationBean<SignatureCheckFilter> registrationBean = new FilterRegistrationBean<>();
        SignatureCheckFilter signatureCheckFilter = newSignatureCheckFilter();
        registrationBean.setFilter(signatureCheckFilter);


        List<String> tempUrlPatterns = (StringUtils.isEmpty(checkMacConfig.getUrlPatterns()))
                ? new ArrayList<String>()
                :Arrays.asList(checkMacConfig.getUrlPatterns().split(","));
        List<String> urlPatterns = Collections.unmodifiableList(tempUrlPatterns);
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

}
