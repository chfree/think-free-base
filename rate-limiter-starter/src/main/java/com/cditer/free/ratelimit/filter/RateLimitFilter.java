package com.cditer.free.ratelimit.filter;

import com.cditer.free.ratelimit.properties.RateLimitProperties;
import com.cditer.free.ratelimit.config.Policy;
import com.cditer.free.ratelimit.config.RateLimitEntity;
import com.cditer.free.ratelimit.config.RateLimitRepository;
import com.cditer.free.ratelimit.config.RateLimitUtils;
import com.cditer.free.ratelimit.exception.RateLimitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
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
				throw new RateLimitException();
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
