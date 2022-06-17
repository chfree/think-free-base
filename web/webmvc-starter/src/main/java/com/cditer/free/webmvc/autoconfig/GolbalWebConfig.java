package com.cditer.free.webmvc.autoconfig;

import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.webmvc.intceptor.IGlobalInterceptorRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Configuration
public class GolbalWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<IGlobalInterceptorRegistry> allInterceptorRegistryList = getAllInterceptorRegistryList();
        if(!CollectionUtils.isEmpty(allInterceptorRegistryList)) {
            for (IGlobalInterceptorRegistry interceptorRegistry : allInterceptorRegistryList) {
                if (interceptorRegistry.getHandlerInterceptor() == null || !StringUtils.hasText(interceptorRegistry.getPatterns())) {
                    continue;
                }
                registry.addInterceptor(interceptorRegistry.getHandlerInterceptor()).addPathPatterns(interceptorRegistry.getPatterns());
            }
        }
        WebMvcConfigurer.super.addInterceptors(registry);
    }


    private List<IGlobalInterceptorRegistry> getAllInterceptorRegistryList(){
        Map<String, IGlobalInterceptorRegistry> beans = SpringContextUtils.getBeans(IGlobalInterceptorRegistry.class);
        if(CollectionUtils.isEmpty(beans)){
            return null;
        }
        return beans.values().stream().sorted(Comparator.comparing(item -> item.getOrder())).collect(Collectors.toList());
    }

}
