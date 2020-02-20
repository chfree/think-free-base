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

package com.tennetcn.free.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

/**
 * 限流类型
 * @author ShanSheng
 *
 */
public enum RateLimitType {
    /**
     * Rate limit policy considering the request path to the downstream service.
     */
    URL {
        @Override
        public boolean apply(HttpServletRequest request,  RateLimitUtils rateLimitUtils, String matcher) {
        	if(StringUtils.isEmpty(matcher)) {
        		return false;
        	}
            return RateLimitType.pathMatch(rateLimitUtils.getUrl(request),matcher.split(","));
        }

        @Override
        public String key(HttpServletRequest request,  RateLimitUtils rateLimitUtils, String matcher) {
        	return matcher;
        }
    },

    /**
     * Rate limit policy considering the IP's origin.
     */
    IP {
        @Override
        public boolean apply(HttpServletRequest request,  RateLimitUtils rateLimitUtils, String matcher) {
            return StringUtils.isEmpty(matcher) || matcher.contains(rateLimitUtils.getIP(request));
        }

        @Override
        public String key(HttpServletRequest request, RateLimitUtils rateLimitUtils, String matcher) {
            return rateLimitUtils.getIP(request);
        }
    },

    /**
     * Rate limit policy considering the authenticated user.
     */
    USER {
        @Override
        public boolean apply(HttpServletRequest request, RateLimitUtils rateLimitUtils, String matcher) {
        	//需要复写获取用户信息的方法
    		String user = rateLimitUtils.getUser(request);
    		//如果用户为空，则不限制
    		if(StringUtils.isEmpty(user)) {
    			return false;
    		}
    		
        	//如果不指定具体用户，则所有用户都需要限制
        	if(StringUtils.isEmpty(matcher)) {
        		return true;
        	}else {
        		
        		if(matcher.contains(rateLimitUtils.getUser(request))) {
        			return true;
        		}
        	}
            return false;
        }

        @Override
        public String key(HttpServletRequest request,  RateLimitUtils rateLimitUtils, String matcher) {
            return rateLimitUtils.getUser(request);
        }
    },


    ;

    public abstract boolean apply(HttpServletRequest request, 
                                  RateLimitUtils rateLimitUtils, String matcher);

    /**
     *	 生成策略对应Limit的主键
     * @param request
     * @param rateLimitUtils
     * @param matcher 待匹配的规则
     * @return
     */
    public abstract String key(HttpServletRequest request, 
                               RateLimitUtils rateLimitUtils, String matcher);
    
    private static final PathMatcher pathMatcher = new AntPathMatcher();
    
    private static boolean pathMatch(String requestPath,String ...paths) {
    	for (String path : paths) {
			if(pathMatcher.match(path, requestPath)) {
				return true;
			}
		}
    	return false;
    }

}
