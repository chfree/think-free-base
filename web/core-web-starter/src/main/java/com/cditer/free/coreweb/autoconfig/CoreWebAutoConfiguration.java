package com.cditer.free.coreweb.autoconfig;

import com.cditer.free.coreweb.http.convert.CustomMappingJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-25 09:29
 * @comment
 */

@Configuration
@PropertySource("classpath:web-boot.properties")
@ImportResource(locations={"classpath:core-web-boot-config.xml"})
public class CoreWebAutoConfiguration {

    @Bean
    @Qualifier("restTemplate")
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        if(CollectionUtils.isEmpty(messageConverters)){
            messageConverters = new ArrayList<>();
        }

        messageConverters.add(new CustomMappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

        return  restTemplate;
    }
}
