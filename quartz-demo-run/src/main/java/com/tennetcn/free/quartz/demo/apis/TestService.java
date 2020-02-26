package com.tennetcn.free.quartz.demo.apis;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-26 21:40
 * @comment
 */

@Component
public class TestService {
    public void test() {
        System.out.print(DateUtil.now());
        System.out.println("test is success");
    }
}
