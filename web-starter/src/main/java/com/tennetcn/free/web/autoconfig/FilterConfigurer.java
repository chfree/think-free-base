package com.tennetcn.free.web.autoconfig;

import com.tennetcn.free.web.configuration.CheckMacConfig;
import com.tennetcn.free.web.configuration.ThinkWebConfig;
import com.tennetcn.free.web.filter.AjaxCorsFilter;
import com.tennetcn.free.web.filter.SignatureCheckFilter;
import com.tennetcn.free.web.filter.TraceIdFilter;
import com.tennetcn.free.web.filter.checkhelper.ICheckHelper;
import com.tennetcn.free.web.filter.checkhelper.MacCheckHelper;
import com.tennetcn.free.web.filter.checkhelper.TimestampCheckHelper;
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
    public AjaxCorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if(webConfig.isCorsEnabled()){
            source.registerCorsConfiguration(webConfig.getCorsPath(), buildConfig());
        }
        return new AjaxCorsFilter(source);
    }

    @Bean
    public FilterRegistrationBean filterRegistTraceIdFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TraceIdFilter traceIdFilter = new TraceIdFilter();
        traceIdFilter.setOrder(101);
        registrationBean.setFilter(traceIdFilter);

        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(3000);

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
    //@ConditionalOnProperty(value = "think.checkmac.enabled",havingValue = "true")
    public FilterRegistrationBean<SignatureCheckFilter> filterRegistSignatureCheckFilter(){
        FilterRegistrationBean<SignatureCheckFilter> registrationBean = new FilterRegistrationBean<>();
        SignatureCheckFilter signatureCheckFilter = newSignatureCheckFilter();
        signatureCheckFilter.setOrder(99);
        registrationBean.setFilter(signatureCheckFilter);


        List<String> tempUrlPatterns = (StringUtils.isEmpty(checkMacConfig.getUrlPatterns()))
                ? new ArrayList<String>()
                :Arrays.asList(checkMacConfig.getUrlPatterns().split(","));
        List<String> urlPatterns = Collections.unmodifiableList(tempUrlPatterns);
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(Integer.MAX_VALUE);
        registrationBean.setOrder(2000);

        return registrationBean;
    }

}
