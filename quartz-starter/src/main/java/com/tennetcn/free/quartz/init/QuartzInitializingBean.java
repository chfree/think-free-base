package com.tennetcn.free.quartz.init;

import com.tennetcn.free.quartz.service.IQuartzService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-26 19:33
 * @comment
 */

@Component
public class QuartzInitializingBean implements InitializingBean {

    @Autowired
    IQuartzService quartzService;

    @Override
    public void afterPropertiesSet() throws Exception {
        quartzService.initAllTaks();
    }
}
