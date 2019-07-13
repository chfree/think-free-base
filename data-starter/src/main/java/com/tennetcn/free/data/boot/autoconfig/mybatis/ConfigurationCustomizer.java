package com.tennetcn.free.data.boot.autoconfig.mybatis;

import org.apache.ibatis.session.Configuration;

/**
 * 仿照了mybatis-spring-boot-autoconfigure包下面的写法
 * 因为在加载的时候，除了自定义加载，还有很多的属于框架性的加载
 * @author chenghuan
 *
 */
public interface ConfigurationCustomizer {
	/**
	 * Customize the given a {@link Configuration} object.
	 * 
	 * @param configuration
	 *            the configuration object to customize
	 */
	void customize(Configuration configuration);
}
