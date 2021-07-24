package com.cditer.free.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

public interface RateLimitUtils {

    /**
     * Returns the authenticated user from {@link HttpServletRequest}.
     *
     * @param request The {@link HttpServletRequest}
     * @return The authenticated user or annonymous
     */
    String getUser(HttpServletRequest request);

    /**
     * Returns the remote IP address from {@link HttpServletRequest}.
     *
     * @param request The {@link HttpServletRequest}
     * @return The remote IP address
     */
    String getIP(HttpServletRequest request);

    /**
     * Returns the url.
     *
     * @return The url
     */
    String getUrl(HttpServletRequest request);

}
