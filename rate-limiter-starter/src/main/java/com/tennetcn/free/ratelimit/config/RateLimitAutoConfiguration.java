/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tennetcn.free.ratelimit.config;

import com.tennetcn.free.ratelimit.support.DefaultRateLimitRepository;
import com.tennetcn.free.ratelimit.filter.RateLimitFilter;
import com.tennetcn.free.ratelimit.properties.RateLimitProperties;
import com.tennetcn.free.ratelimit.support.DefaultRateLimitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 
 * @author ShanSheng
 *
 */
@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@Slf4j
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
