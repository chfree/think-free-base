package com.cditer.free.core.properties;

import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 *
 */
@Configuration
public class CacheProperties {
	
	public static final String CACHE_NAME = "free";
	
	/**
	 * 缓存仓库
	 */
	private String repo = "IN_MEMORY";

	/**
	 * 缓存有效期时间(秒)
	 */
	private int ttl = 3600;
	
	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
	
}
