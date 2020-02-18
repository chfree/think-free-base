package com.tennetcn.free.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;//声明一个静态变量保存
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        if(SpringContextUtil.context==null) {
            SpringContextUtil.context=applicationContext;
        }
    }
    public static ApplicationContext getCurrentContext(){
        return context;
    }

    public static void setCurrentContext(ApplicationContext applicationContext) {
        context=applicationContext;
    }
}
