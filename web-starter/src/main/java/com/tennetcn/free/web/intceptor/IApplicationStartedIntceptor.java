package com.tennetcn.free.web.intceptor;

import org.springframework.context.event.ContextRefreshedEvent;

public interface IApplicationStartedIntceptor {
    void started(ContextRefreshedEvent contextRefreshedEvent);
}
