package com.cditer.free.core.cache;

import net.sf.ehcache.Element;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-25 09:41
 * @comment
 */

public interface ICached {
    void put(String key,Object value);

    Object get(String key);

    <T> T get(String key,Class<T> tClass);

    void remove(String key);

    void put(Element element);

    /**
     *
     * @param key
     * @param value
     * @param timeToLive 缓存自创建日期起至失效时的间隔时间
     * @param timeToIdle 缓存创建以后，最后一次访问缓存的日期至失效之时的时间间隔
     * example:
     * timeToIdleSeconds=120；
     * timeToLiveSeconds=180；
     * 上面的表示此缓存最多可以存活3分钟，如果期间超过2分钟未访问 那么此缓存失效！
     */
    void put(String key,String value,int timeToLive,int timeToIdle);

    /**
     *
     * @param key
     * @param value
     * @param timeToIdle 缓存创建以后，最后一次访问缓存的日期至失效之时的时间间隔
     * timeToLive default 120
     */
    void put(String key,String value,int timeToIdle);
}
