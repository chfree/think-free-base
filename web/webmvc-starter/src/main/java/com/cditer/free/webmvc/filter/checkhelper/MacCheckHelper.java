package com.cditer.free.webmvc.filter.checkhelper;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Locale;

@Component
public class MacCheckHelper extends AbstractCheckHelper {
    /**
     * 检查Hmac方法
     * @param request
     * @param response
     * @throws ServletException 检查不通过抛出异常
     * @return true 检查通过 false 检查不通过
     */
    public boolean chekcMac(ServletRequest request, ServletResponse response) throws ServletException {
        if (!checkMacConfig.isEnabled()) {
            //开关关闭，不进行校验
            return true;
        }

        final HttpServletRequestWrapper requestWrapper = (HttpServletRequestWrapper) request;

        final String sha1Val = DigestUtil.sha1Hex(getInput4Hmac(requestWrapper));
        final String calculatedHmac = DigestUtil.md5Hex(sha1Val.toLowerCase(Locale.ENGLISH));
        final String requestHmac = getRequestHmac(requestWrapper);

        return requestHmac.equalsIgnoreCase(calculatedHmac);
    }
}
