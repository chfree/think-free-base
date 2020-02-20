package com.tennetcn.free.ratelimit.properties;


import com.google.common.collect.Lists;
import com.tennetcn.free.ratelimit.config.Policy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 
 * @author ShanSheng
 *
 */
@Data
@Configuration
@ConfigurationProperties(RateLimitProperties.PREFIX)
public class RateLimitProperties {
	
    public static final String PREFIX = "think.ratelimit";
    
    private boolean enabled = false;
    
    private List<Policy> policyList = Lists.newArrayList();
    
    private String cacheName = "ratelimit";
}
