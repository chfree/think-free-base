package com.cditer.free.webmvc.filter.checkhelper;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Date;
import java.util.Locale;

@Component
public class TimestampCheckHelper extends AbstractCheckHelper {

    public boolean chekcMac(final ServletRequest request, final ServletResponse response) throws ServletException {
        if (!checkMacConfig.isEnabled()) {
            //开关关闭，不进行校验
            return true;
        }

        final HttpServletRequestWrapper requestWrapper = (HttpServletRequestWrapper) request;

        final long clientTime = getTimestamp(requestWrapper);
        final long currentServerTime = System.currentTimeMillis();

        if (Math.abs((currentServerTime - clientTime) / 1000) > checkMacConfig.getTimeThreshold()) {
            throw new IllegalStateException("#macCheck# Client timeout. Client time is " + new Date(clientTime).toString());
        }


        final String sha1Val = DigestUtil.sha1Hex(checkMacConfig.getSalt() + clientTime);
        final String calculatedHmac = DigestUtil.md5Hex(sha1Val.toLowerCase(Locale.ENGLISH));
        final String requestHmac = getRequestHmac(requestWrapper);

        return requestHmac.equalsIgnoreCase(calculatedHmac);
    }

    /**
     * 获得请求Uri
     * @param wrapper
     * @return
     */
    protected long getTimestamp(HttpServletRequestWrapper wrapper) {
        final String TIMESTAMP_FIELD_NAME = checkMacConfig.getTimestampFieldName();
        final String timestamp = (wrapper.getHeader(TIMESTAMP_FIELD_NAME) == null)?wrapper.getParameter(TIMESTAMP_FIELD_NAME):wrapper.getHeader(TIMESTAMP_FIELD_NAME);
        if (timestamp == null) {
            throw new IllegalStateException("#macCheck# Request does not have timestamp field");
        }

        try {
            final long ret = Long.parseLong(timestamp);
            return ret;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("#macCheck# Request timestamp is not invalid number");
        }
    }
}
