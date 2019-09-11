package com.tennetcn.free.activiti.message;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-11 12:18
 * @comment
 */

@Data
public class DeployModel {
    private String name;

    private String category;

    private DeployResource deployResource;
}
