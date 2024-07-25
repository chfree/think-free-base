package com.cditer.free.quartz.init;

import com.cditer.free.quartz.autoconfig.QuartzConfig;
import com.cditer.free.quartz.service.IQuartzService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-26 19:33
 * @comment
 */

@Component
public class QuartzInitializingBean implements InitializingBean {

    @Autowired
    private IQuartzService quartzService;

    @Autowired
    private QuartzConfig quartzConfig;

    @Override
    public void afterPropertiesSet() throws Exception {

        String scope = quartzConfig.getScope();
        quartzService.initAllTask(scope);

        quartzService.registerListener();
    }
}
