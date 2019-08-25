package com.tennetcn.free.core.cache;

import com.tennetcn.free.core.properties.CacheProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * 缓存实现
 *
 */
@Component
public class CachedImpl implements ICached {

	@Autowired
	private CacheManager cacheManager;
	

	@Override
	public void put(String key, Object value) {
		cacheManager.getCache(CacheProperties.CACHE_NAME).put(key, value);
	}

	@Override
	public Object get(String key) {
		ValueWrapper valueWrapper = cacheManager.getCache(CacheProperties.CACHE_NAME).get(key);
		if(valueWrapper != null) {
			return valueWrapper.get();
		}else {
			return null;
		}
	}

	@Override
	public void remove(String key) {
		cacheManager.getCache(CacheProperties.CACHE_NAME).evict(key);
	}



}
