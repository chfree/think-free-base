package com.tennetcn.free.web.base;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import java.util.Date;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-03-02 23:38
 * @comment
 */

@Component
public class ThinkWebBindingInitializer implements WebBindingInitializer {
    public ThinkWebBindingInitializer(){
        System.out.println("ThinkWebBindingInitializer----");
    }
    @Override
    public void initBinder(WebDataBinder webDataBinder) {

        System.out.println("initBinder-------webDataBinder");
        webDataBinder.registerCustomEditor(Date.class, new DateEditor());
        webDataBinder.registerCustomEditor(Integer.class,new IntegerEditor());
        webDataBinder.registerCustomEditor(Double.class,new DoubleEditor());
        webDataBinder.registerCustomEditor(Float.class,new FloatEditor());
        webDataBinder.registerCustomEditor(Boolean.class,new BooleanEditor());
    }
}
