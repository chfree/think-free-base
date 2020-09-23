package com.tennetcn.free.web.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.tennetcn.free.core.util.CommonUtils;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-10 11:55
 * @comment
 */

public class TraceIdFilter implements OrderedFilter {
    private int order = 100;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String traceId = CommonUtils.getTraceId();
        request.setAttribute("traceId", traceId);

        HttpServletResponse response = (HttpServletResponse) res;
        try {
            HttpServletRequest req = ((HttpServletRequest) request);
            MDC.put("reqUrl", req.getRequestURI());

            response.setHeader("trace-id", traceId);
            MDC.put("bussiness", traceId);
        }catch(Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}