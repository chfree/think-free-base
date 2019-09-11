package com.tennetcn.free.activiti.message;

import lombok.Data;

import java.io.InputStream;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-11 12:16
 * @comment
 */

@Data
public class DeployResource {
    private String resourceName;

    private InputStream inputStream;

    private String classpathResource;

    private byte[] bytes;
}
