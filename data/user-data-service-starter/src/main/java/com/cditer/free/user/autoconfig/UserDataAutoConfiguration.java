package com.cditer.free.user.autoconfig;

import org.springframework.context.annotation.ImportResource;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-23 22:28
 * @comment
 */

@MapperScan(value = "com.cditer.free.user.logical.mapper")
@ImportResource(locations={"classpath:user-data-boot-config.xml"})
public class UserDataAutoConfiguration {
}
