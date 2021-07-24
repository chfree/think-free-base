package com.cditer.free.data.boot.autoconfig.mybatis;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.VFS;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author chenghuan
 */
public class SpringBootVFS extends VFS {

	private final ResourcePatternResolver resourceResolver;

	public SpringBootVFS() {
		this.resourceResolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	protected List<String> list(URL url, String path) throws IOException {
		Resource[] resources = resourceResolver.getResources("classpath*:" + path + "/**/*.class");
		List<String> resourcePaths = new ArrayList<String>();
		for (Resource resource : resources) {
			resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
		}
		return resourcePaths;
	}

	private static String preserveSubpackageName(final URI uri, final String rootPath) {
		final String uriStr = uri.toString();
		final int start = uriStr.indexOf(rootPath);
		return uriStr.substring(start);
	}

}
