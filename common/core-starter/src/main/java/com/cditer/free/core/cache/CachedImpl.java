package com.cditer.free.core.cache;

import com.cditer.free.core.properties.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * 缓存实现
 *
 */
@Slf4j
@Component
public class CachedImpl implements ICached {

	@Autowired
	private CacheManager cacheManager;
	

	@Override
	public void put(String key, Object value) {
		log.info("cacheManager is {}",cacheManager.getClass().getName());
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
	public <T> T get(String key, Class<T> tClass) {
		Object object = get(key);
		if(object==null){
			return null;
		}
		return (T)object;
	}

	@Override
	public void remove(String key) {
		cacheManager.getCache(CacheProperties.CACHE_NAME).evict(key);
	}

	public void put(Element element){
		Object nativeCache = cacheManager.getCache(CacheProperties.CACHE_NAME).getNativeCache();
		if(nativeCache instanceof Ehcache){ // 如果是ehcache则支持element
			Ehcache ehcache = (Ehcache)nativeCache;
			ehcache.put(element);
		}else{
			this.put(element.getObjectKey().toString(),element.getObjectValue());
		}

	}

	@Override
	public void put(String key, String value, int timeToLive, int timeToIdle) {
		Element element = new Element(key,value);
		element.setTimeToIdle(timeToIdle);
		element.setTimeToLive(timeToLive);

		put(element);
	}

	@Override
	public void put(String key, String value, int timeToIdle) {
		Element element = new Element(key,value);
		element.setTimeToIdle(timeToIdle);
		element.setTimeToLive(120);

		put(element);
	}

}
