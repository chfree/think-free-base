package com.cditer.free.ratelimit.properties;


import com.google.common.collect.Lists;
import com.cditer.free.ratelimit.config.Policy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(RateLimitProperties.PREFIX)
public class RateLimitProperties {
	
    public static final String PREFIX = "think.ratelimit";
    
    private boolean enabled = false;
    
    private List<Policy> policyList = Lists.newArrayList();
    
    private String cacheName = "ratelimit";
}
