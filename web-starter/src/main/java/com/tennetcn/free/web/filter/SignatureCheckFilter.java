package com.tennetcn.free.web.filter;

import cn.hutool.json.JSONUtil;
import com.tennetcn.free.core.exception.BizException;
import com.tennetcn.free.core.message.web.BaseResponse;
import com.tennetcn.free.core.message.web.ResponseStatus;
import com.tennetcn.free.web.configuration.CheckMacConfig;
import com.tennetcn.free.web.filter.checkhelper.CachingRequestWrapper;
import com.tennetcn.free.web.filter.checkhelper.ICheckHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.annotation.Order;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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
@Order(100000)
public class SignatureCheckFilter implements OrderedFilter {
    private int order = 1005;
    public static final String CHECK_STRATEGY_MAC = "mac";
    public static final String CHECK_STRATEGY_TIMESTAMP = "timestamp";

    private ICheckHelper helper;
    private List<String> uriWhiteList;

    CheckMacConfig checkMacConfig;

    public SignatureCheckFilter(ICheckHelper helper,CheckMacConfig checkMacConfig){
        this.helper = helper;
        this.checkMacConfig = checkMacConfig;
        setOrder(checkMacConfig.getFilterOrder());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("SignatureCheckFilter init");
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
        HttpServletRequest req = (HttpServletRequest) request;
        if("options".equals(req.getMethod().toLowerCase())){
            chain.doFilter(request,response);
            return;
        }
        final String requestURI = req.getRequestURI();
        boolean isWhiteListIncluded = false;
        for (String uri : uriWhiteList) {
            if (requestURI.startsWith(uri)) {
                isWhiteListIncluded = true;
                break;
            }
        }
        if (isWhiteListIncluded || isMultipart(req)) {
            chain.doFilter(request, response);
        }else{
            final CachingRequestWrapper wrapper = new CachingRequestWrapper(req);
            wrapper.getInputStream();
            try {
                if (helper.chekcMac(wrapper, response)) {
                    chain.doFilter(wrapper, response);
                } else {
                    throw new BizException("check mac faild");
                }
            }catch (Exception ex){
                BaseResponse resp = new BaseResponse();
                resp.setStatus(ResponseStatus.SIGNATURE_ERROR);
                resp.setMessage(ex.getMessage());

                response.setContentType("application/json; charset=utf-8");

                String mapJsonStr= JSONUtil.toJsonStr(resp);
                OutputStream os =  response.getOutputStream();
                os.write(mapJsonStr.getBytes());
                os.flush();
                os.close();
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

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
