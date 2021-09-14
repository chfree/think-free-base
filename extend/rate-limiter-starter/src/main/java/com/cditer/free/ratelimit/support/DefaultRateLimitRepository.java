package com.cditer.free.ratelimit.support;

import com.cditer.free.ratelimit.properties.RateLimitProperties;
import com.cditer.free.ratelimit.config.RateLimitEntity;
import com.cditer.free.ratelimit.config.RateLimitRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

@AllArgsConstructor
public class DefaultRateLimitRepository implements RateLimitRepository {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private RateLimitProperties rateLimitProperties;
	
	@Override
	public void put(String key, RateLimitEntity value) {
		cacheManager.getCache(rateLimitProperties.getCacheName()).put(key, value);
	}

	@Override
	public RateLimitEntity get(String key) {
		ValueWrapper valueWrapper = cacheManager.getCache(rateLimitProperties.getCacheName()).get(key);
		if(valueWrapper != null) {
			return (RateLimitEntity) valueWrapper.get();
		}else {
			return null;
		}
//		return cacheManager.getCache(CacheProperties.CACHE_NAME).get(key).get();
	}

	@Override
	public void remove(String key) {
		cacheManager.getCache(rateLimitProperties.getCacheName()).evict(key);
	}

	@Override
	public void clear() {
		cacheManager.getCache(rateLimitProperties.getCacheName()).clear();
	}



}
