package com.tennetcn.free.ratelimit.config;

public interface RateLimitRepository {

	/**
	 * 获取限流对象
	 */
	public RateLimitEntity get(String key);
	
	public void remove(String key);
	
	public void put(String key, RateLimitEntity entity);
	
	public void clear();
}
