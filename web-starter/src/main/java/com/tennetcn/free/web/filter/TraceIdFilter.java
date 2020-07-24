package com.tennetcn.free.web.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.slf4j.MDC;
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

public class TraceIdFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String traceId = getTraceId();
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

    /**
     * 全局唯一跟踪号
     * @return
     */
    private String getTraceId() {
        String hostName=getHostName();
        if(hostName.length()>=5) {
            hostName=hostName.substring(0, 5);
        }else {

            hostName=org.apache.commons.lang3.StringUtils.leftPad(hostName, 5,"X");
        }

        String currentDate = DateUtil.format(DateUtil.date(),"yyyyMMddHHmmss");
        String rndNum = RandomUtil.randomInt(100000, 999999)+""+RandomUtil.randomInt(10000, 99999);
        return hostName+currentDate+rndNum;
    }

    //获得机器名称,为空则返回'UNHST'-unknow host
    private String getHostName() {
        String hostName = "UNHST";
        try{
            InetAddress addr = InetAddress.getLocalHost();
            if(!StringUtils.isEmpty(addr.getHostName())) {
                hostName= addr.getHostName();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return hostName;
    }
}