package com.cditer.free.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;//声明一个静态变量保存
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        if(SpringContextUtils.context==null) {
            SpringContextUtils.context=applicationContext;
        }
    }
    public static ApplicationContext getCurrentContext(){
        return context;
    }

    public static void setCurrentContext(ApplicationContext applicationContext) {
        context=applicationContext;
    }

    public static <T> T getBean(Class<?> clazz){
        ApplicationContext currentContext = getCurrentContext();
        if(currentContext==null){
            return null;
        }
        return (T)currentContext.getBean(clazz);
    }
}
