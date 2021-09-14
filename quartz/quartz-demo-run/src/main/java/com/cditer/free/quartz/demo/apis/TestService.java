package com.cditer.free.quartz.demo.apis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-26 21:40
 * @comment
 */

@Component
public class TestService {
    public void test() throws Exception{
        long sleepTime = RandomUtil.randomLong(1000,2000);
        System.out.println("sleepTime:"+sleepTime);
        Thread.sleep(sleepTime);
        System.out.print(DateUtil.now());
        System.out.println("test is success");
    }
}
