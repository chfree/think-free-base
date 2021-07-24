package com.cditer.free.web.listener;

import com.cditer.free.web.configuration.ThinkWebConfig;
import com.cditer.free.web.intceptor.IApplicationStartedIntceptor;
import com.cditer.free.core.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BaseApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ThinkWebConfig webConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("application listener run onApplicationEvent");
        SpringContextUtils.setCurrentContext(contextRefreshedEvent.getApplicationContext());
        //执行applicationstart的intceptor
        Map<String, IApplicationStartedIntceptor> applicationStartedMap= SpringContextUtils.getCurrentContext().getBeansOfType(IApplicationStartedIntceptor.class);
        if(applicationStartedMap!=null) {
            for (IApplicationStartedIntceptor applicationStarted : applicationStartedMap.values()) {
                applicationStarted.started(contextRefreshedEvent);
            }
        }
        log.info("started on port(s): "+webConfig.getPort()+" (http) with context path '"+webConfig.getContextPath()+"'");
        log.info("Application is started successfully!!!!");
    }
}
