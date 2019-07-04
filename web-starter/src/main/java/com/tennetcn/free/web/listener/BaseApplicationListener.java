package com.tennetcn.free.web.listener;

import com.tennetcn.free.core.utils.CommonApplicationContextUtil;
import com.tennetcn.free.web.intceptor.IApplicationStartedIntceptor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //执行applicationstart的intceptor
        Map<String, IApplicationStartedIntceptor> applicationStartedMap= CommonApplicationContextUtil.getCurrentContext().getBeansOfType(IApplicationStartedIntceptor.class);
        if(applicationStartedMap!=null) {
            for (IApplicationStartedIntceptor applicationStarted : applicationStartedMap.values()) {
                applicationStarted.started(contextRefreshedEvent);
            }
        }
    }
}
