package com.cditer.free.quartz.demo.apis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.cditer.free.quartz.logical.viewmodel.TaskExecResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-26 21:40
 * @comment
 */

@Slf4j
@Component
public class TestService {
    public TaskExecResult test() throws Exception{
        long sleepTime = RandomUtil.randomLong(1000,2000);
        log.info("sleepTime:"+sleepTime);
        Thread.sleep(sleepTime);
        log.info(DateUtil.now());
        log.info("test is success");

        return TaskExecResult.newResult(true, "info", String.format("执行时长:%s", sleepTime));
    }
}
