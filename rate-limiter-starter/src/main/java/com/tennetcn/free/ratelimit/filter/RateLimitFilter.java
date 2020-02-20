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

package com.tennetcn.free.ratelimit.filter;

import com.tennetcn.free.ratelimit.config.Policy;
import com.tennetcn.free.ratelimit.config.RateLimitEntity;
import com.tennetcn.free.ratelimit.config.RateLimitRepository;
import com.tennetcn.free.ratelimit.config.RateLimitUtils;
import com.tennetcn.free.ratelimit.properties.RateLimitProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author ShanSheng
 *
 */
@RequiredArgsConstructor
@Slf4j
public class RateLimitFilter implements Filter {

	private final RateLimitProperties properties;
	private final RateLimitUtils rateLimitUtils;

	/**
	 * 需要优化：如果限流器里面没有内容，需要清除
	 */
	@Autowired
	private RateLimitRepository rateLimitRepository;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//判断是否需要限流
		if(!shouldFilter(request)) {
			chain.doFilter(request, response);
			return;
		}
		
		policy(request).forEach(policy -> {
//			String key = policy.getEntityKey((HttpServletRequest) request, rateLimitUtils);
			String key = policy.getType().key((HttpServletRequest)request, rateLimitUtils,policy.getMatcher());
			RateLimitEntity rateLimiterEntity = rateLimitRepository.get(key);
			
			if(rateLimiterEntity == null) {
				rateLimiterEntity = new RateLimitEntity(key, policy);
				rateLimitRepository.put(key, rateLimiterEntity);
			}
			//会阻塞吗？
			if(!rateLimiterEntity.consume(1)) {
				throw new RuntimeException("系统忙，请稍后再试");
			}
		});
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("RateLimitFilter init");
//		rateLimitRepository = new ConcurrentHashMap<String, RateLimitEntity>(properties.getPolicyList().size());
	}

	public boolean shouldFilter(ServletRequest request) {
		if(!properties.isEnabled()) {
			rateLimitRepository.clear();
		}
		return properties.isEnabled() && !policy(request).isEmpty();
	}

	/**
	 * 根据请求筛选出符合条件的限流策略
	 * @param request
	 * @return
	 */
	protected List<Policy> policy(ServletRequest request) {
		return properties.getPolicyList().stream().filter(policy -> policy.getType()
				.apply((HttpServletRequest) request, rateLimitUtils, policy.getMatcher()))
				.collect(Collectors.toList());
	}
	

}
