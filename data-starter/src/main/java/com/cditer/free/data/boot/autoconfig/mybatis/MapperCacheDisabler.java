package com.cditer.free.data.boot.autoconfig.mybatis;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-13 13:03
 * @comment
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class MapperCacheDisabler implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MapperCacheDisabler.class);

    public MapperCacheDisabler() {
    }

    public void afterPropertiesSet() {
        this.disableCaching();
    }

    private void disableCaching() {
        try {
            ClassLoader appClassLoader = this.getClass().getClassLoader();
            this.removeStaticCache(ClassUtils.forName("tk.mybatis.mapper.util.MsUtil", appClassLoader), "CLASS_CACHE");
            this.removeStaticCache(ClassUtils.forName("tk.mybatis.mapper.genid.GenIdUtil", appClassLoader));
            this.removeStaticCache(ClassUtils.forName("tk.mybatis.mapper.version.VersionUtil", appClassLoader));
            this.removeEntityHelperCache(ClassUtils.forName("tk.mybatis.mapper.mapperhelper.EntityHelper", appClassLoader));
        } catch (Exception var2) {
        }

    }

    private void removeStaticCache(Class<?> utilClass) {
        this.removeStaticCache(utilClass, "CACHE");
    }

    private void removeStaticCache(Class<?> utilClass, String fieldName) {
        try {
            Field cacheField = ReflectionUtils.findField(utilClass, fieldName);
            if (cacheField != null) {
                ReflectionUtils.makeAccessible(cacheField);
                Object cache = ReflectionUtils.getField(cacheField, (Object)null);
                if (cache instanceof Map) {
                    ((Map)cache).clear();
                } else {
                    if (!(cache instanceof Cache)) {
                        throw new UnsupportedOperationException("cache field must be a java.util.Map or org.apache.ibatis.cache.Cache instance");
                    }

                    ((Cache)cache).clear();
                }

                logger.info("Clear " + utilClass.getCanonicalName() + " " + fieldName + " cache.");
            }
        } catch (Exception var5) {
            logger.warn("Failed to disable " + utilClass.getCanonicalName() + " " + fieldName + " cache. ClassCastExceptions may occur", var5);
        }

    }

    private void removeEntityHelperCache(Class<?> entityHelper) {
        try {
            Field cacheField = ReflectionUtils.findField(entityHelper, "entityTableMap");
            if (cacheField != null) {
                ReflectionUtils.makeAccessible(cacheField);
                Map cache = (Map)ReflectionUtils.getField(cacheField, (Object)null);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                Iterator var5 = (new ArrayList(cache.keySet())).iterator();

                while(var5.hasNext()) {
                    Object key = var5.next();
                    Class entityClass = (Class)key;
                    if (!entityClass.getClassLoader().equals(classLoader)) {
                        cache.remove(entityClass);
                    }
                }

                logger.info("Clear EntityHelper entityTableMap cache.");
            }
        } catch (Exception var8) {
            logger.warn("Failed to disable Mapper MsUtil cache. ClassCastExceptions may occur", var8);
        }

    }
}

