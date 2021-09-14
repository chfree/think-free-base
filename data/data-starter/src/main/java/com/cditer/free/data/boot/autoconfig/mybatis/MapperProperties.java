package com.cditer.free.data.boot.autoconfig.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.mybatis.mapper.entity.Config;

@ConfigurationProperties(
        prefix = "mapper"
)
public class MapperProperties extends Config {
  public MapperProperties() {
  }
}