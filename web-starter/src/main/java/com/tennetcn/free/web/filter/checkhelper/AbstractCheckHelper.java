package com.tennetcn.free.web.filter.checkhelper;

import com.tennetcn.free.web.configuration.CheckMacConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractCheckHelper implements ICheckHelper {

    @Autowired
    protected CheckMacConfig checkMacConfig;

    /**
     * 获得要计算的报文内容范围。默认实现为请求Uri+请求参数+请求报文体
     * @param wrapper
     * @return
     */
    protected String getInput4Hmac(HttpServletRequestWrapper wrapper) {
        final StringBuilder sb = new StringBuilder(checkMacConfig.getSalt());
        sb.append(null2Empty(getUri(wrapper)));
        sb.append(null2Empty(getQueryString(wrapper)));
        sb.append(null2Empty(getBody(wrapper)));
        return sb.toString();
    }

    /**
     * 获得输入的散列签名值。默认从Http报文头部指定字段中获取
     * @param wrapper
     * @return
     */
    protected String getRequestHmac(HttpServletRequestWrapper wrapper) {
        String headerFieldName = checkMacConfig.getHeaderFieldName();
        final String signature = (wrapper.getHeader(headerFieldName) == null)?wrapper.getParameter(headerFieldName):wrapper.getHeader(headerFieldName);
        if (signature == null) {
            throw new IllegalStateException("#macCheck# Request does not have signature field");
        }
        return signature;
    }

    /**
     * 获得请求Uri
     * @param wrapper
     * @return
     */
    protected String getUri(HttpServletRequestWrapper wrapper) {
        return wrapper.getRequestURI();
    }

    /**
     * 获得请求参数
     * @param wrapper
     * @return
     */
    protected String getQueryString(HttpServletRequestWrapper wrapper) {
        return wrapper.getQueryString();
    }

    /**
     * 获得请求报文体
     * @param wrapper
     * @return
     */
    protected String getBody(HttpServletRequestWrapper wrapper) {
        final StringBuilder sb = new StringBuilder();
        String temp = null;
        try {
            final BufferedReader reader = wrapper.getReader();

            while ((temp=reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            throw new IllegalStateException("#macCheck# Failed to read request body", e);
        }

        return sb.toString();
    }

    private String null2Empty(String input) {
        return (null == input) ? "" : input;
    }
}
