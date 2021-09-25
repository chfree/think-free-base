package com.cditer.free.coreweb.intceptor;

import org.springframework.context.event.ContextRefreshedEvent;

public interface IApplicationStartedIntceptor {
    void started(ContextRefreshedEvent contextRefreshedEvent);
}
