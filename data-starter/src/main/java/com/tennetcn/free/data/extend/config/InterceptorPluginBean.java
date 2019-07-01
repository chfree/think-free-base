package com.tennetcn.free.data.extend.config;

import org.apache.ibatis.plugin.Interceptor;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2018年3月28日 上午11:09:29
 * @comment 
 */

public class InterceptorPluginBean {
	private Interceptor[] plugins;

	public Interceptor[] getPlugins() {
		return plugins;
	}

	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}
}
