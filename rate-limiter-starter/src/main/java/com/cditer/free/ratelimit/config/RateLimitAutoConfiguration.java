package com.cditer.free.ratelimit.config;

import com.cditer.free.ratelimit.filter.RateLimitFilter;
import com.cditer.free.ratelimit.properties.RateLimitProperties;
import com.cditer.free.ratelimit.support.DefaultRateLimitRepository;
import com.cditer.free.ratelimit.support.DefaultRateLimitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Filter;


@Slf4j
@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@ImportResource(locations={"ratelimit-spring-boot-config.xml"})
public class RateLimitAutoConfiguration {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private RateLimitProperties rateLimitProperties;

	@Bean
	public Filter rateLimiterPreFilter(final RateLimitProperties rateLimitProperties,
			final RateLimitUtils rateLimitUtils) {
		log.info("rateLimiterPreFilter:" + rateLimitProperties);
		return new RateLimitFilter(rateLimitProperties, rateLimitUtils);
	}

	@Bean
	@ConditionalOnMissingBean(CacheManager.class)
	public CacheManager memoryCacheManager() {
		return new ConcurrentMapCacheManager();
	}

//	@Configuration
//	@ConditionalOnMissingBean(RateLimitUtils.class)
//	public static class RateLimitUtilsConfiguration {
//
//		@Bean
//		public RateLimitUtils rateLimitUtils() {
//			return new DefaultRateLimitUtils();
//		}
//	}
	
	@Bean
	@ConditionalOnMissingBean(RateLimitUtils.class)
	public RateLimitUtils rateLimitUtils() {
		return new DefaultRateLimitUtils();
	}

	@Bean
	@ConditionalOnMissingBean(RateLimitRepository.class)
	public RateLimitRepository rateLimitRepository() {
		return new DefaultRateLimitRepository(cacheManager, rateLimitProperties);
	}
}
