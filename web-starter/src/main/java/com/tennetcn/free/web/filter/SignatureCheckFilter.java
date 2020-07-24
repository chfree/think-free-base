package com.tennetcn.free.web.filter;

import com.tennetcn.free.core.util.SpringContextUtils;
import com.tennetcn.free.web.configuration.CheckMacConfig;
import com.tennetcn.free.web.filter.checkhelper.CachingRequestWrapper;
import com.tennetcn.free.web.filter.checkhelper.ICheckHelper;
import com.tennetcn.free.web.filter.checkhelper.MacCheckHelper;
import com.tennetcn.free.web.filter.checkhelper.TimestampCheckHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-07-22 18:42
 * @comment
 */
@Slf4j
public class SignatureCheckFilter implements Filter {
    public static final String CHECK_STRATEGY_MAC = "mac";
    public static final String CHECK_STRATEGY_TIMESTAMP = "timestamp";

    private ICheckHelper helper;
    private List<String> uriWhiteList;

    @Autowired
    CheckMacConfig checkMacConfig;

    @Autowired
    MacCheckHelper macCheckHelper;

    @Autowired
    TimestampCheckHelper timestampCheckHelper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("SignatureCheckFilter init");
        if(checkMacConfig.getStrategy() == null || checkMacConfig.getStrategy().equalsIgnoreCase(CHECK_STRATEGY_MAC)){
            helper = macCheckHelper;
        }else if(checkMacConfig.getStrategy().equalsIgnoreCase(CHECK_STRATEGY_TIMESTAMP)){
            helper = timestampCheckHelper;
        }else{
            throw new IllegalArgumentException("Input Strategy is Error! input value is " + checkMacConfig.getStrategy() + "must is " + CHECK_STRATEGY_MAC + " or " + CHECK_STRATEGY_TIMESTAMP);
        }
        List<String> tempAllowlist = (checkMacConfig.getAllowlist() != null && checkMacConfig.getAllowlist().length() > 0)
                ? Arrays.asList(checkMacConfig.getAllowlist().split(","))
                :new ArrayList<String>();
        uriWhiteList = Collections.unmodifiableList(tempAllowlist);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("#signatureCheck# SignatureCheckFilter only supports HTTP requests");
        }
        final String requestURI = ((HttpServletRequest) request).getRequestURI();
        boolean isWhiteListIncluded = false;
        for (String uri : uriWhiteList) {
            if (requestURI.startsWith(uri)) {
                isWhiteListIncluded = true;
                break;
            }
        }

        if (isWhiteListIncluded || isMultipart((HttpServletRequest) request)) {
            chain.doFilter(request, response);
        }else{
            final CachingRequestWrapper wrapper = new CachingRequestWrapper((HttpServletRequest) request);
            wrapper.getInputStream();
            if (helper.chekcMac(wrapper,response)) {
                chain.doFilter(wrapper, response);
            }else{
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Mac check failed");
            }
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断是否是multipart请求
     * @param request
     * @return
     */
    private boolean isMultipart(HttpServletRequest request) {
        if (!"post".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        return startsWithIgnoreCase(request.getContentType(), "multipart/");
    }

    private boolean startsWithIgnoreCase(String str, String prefix) {
        return (str != null && prefix != null && str.length() >= prefix.length() &&
                str.regionMatches(true, 0, prefix, 0, prefix.length()));
    }
}
