package com.cditer.free.ratelimit.support;

import com.cditer.free.ratelimit.config.RateLimitUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 默认工具类
 */
@RequiredArgsConstructor
public class DefaultRateLimitUtils implements RateLimitUtils {

	@Override
	public String getUser(final HttpServletRequest request) {
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public String getIP(HttpServletRequest request) {
		if (request == null) {
			return "";
		}

		String ip = request.getHeader("X-Forwarded-For");

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_Client_IP");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {

			}
		}

		return ip;
	}

	@Override
	public String getUrl(final HttpServletRequest request) {
		return request.getRequestURI();
	}
}
