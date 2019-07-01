package com.tennetcn.free.data.extend.config;

import org.springframework.core.io.Resource;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2018年3月28日 上午11:07:35
 * @comment 
 */

public class MapperLocationBean {
	private Resource[] mapperLocations;

	public Resource[] getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}
	
	
}
