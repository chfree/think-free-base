package com.tennetcn.free.ratelimit.config;

import java.time.LocalTime;

import com.google.common.util.concurrent.RateLimiter;

import lombok.Data;

@Data
public class RateLimitEntity {

	private String key;
	
	private Policy policy;
	
	private RateLimiter rateLimiter;
	
	/**
	 * 创建时间
	 */
	private LocalTime createTime;
	

	public RateLimitEntity(final String key ,final Policy policy) {
		this.key = key;
		this.policy = policy;
		this.rateLimiter = RateLimiter.create(policy.getLimit());
		this.createTime = LocalTime.now();
	}
	
	public boolean consume(int permits) {
		return this.rateLimiter.tryAcquire(permits);
	}

}
