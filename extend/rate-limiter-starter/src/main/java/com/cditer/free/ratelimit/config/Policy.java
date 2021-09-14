package com.cditer.free.ratelimit.config;

import static java.util.concurrent.TimeUnit.MINUTES;

import lombok.Data;

@Data
public class Policy {

	/**
	 * 限流类型
	 */
	private RateLimitType type;

	/**
	 * 限流窗口时长
	 */
	private Long refreshInterval = MINUTES.toSeconds(1L);

	/**
	 * 限流次数
	 */
	private Long limit;

	/**
	 * 限流条件(根据type内容不同)
	 */
	private String matcher;
}
