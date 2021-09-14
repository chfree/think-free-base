package com.cditer.free.web.webapi;

import com.cditer.free.core.cache.ICached;
import com.cditer.free.web.base.BooleanEditor;
import com.cditer.free.web.base.DateEditor;
import com.cditer.free.web.base.DoubleEditor;
import com.cditer.free.web.base.FloatEditor;
import com.cditer.free.web.base.IntegerEditor;
import com.cditer.free.web.base.ModelStatusEditor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Data
public abstract class FirstApi {

    @Autowired
    protected HttpServletRequest servletRequest;

    @Autowired
    protected HttpServletResponse servletResponse;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected ICached cached;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(Integer.class,new IntegerEditor());
        binder.registerCustomEditor(Double.class,new DoubleEditor());
        binder.registerCustomEditor(Float.class,new FloatEditor());
        binder.registerCustomEditor(Boolean.class,new BooleanEditor());

        tryRegisterCustomEditor(binder);
    }

    private void tryRegisterCustomEditor(WebDataBinder binder){
        try {
            Class<?> modelStatusClass= Class.forName("com.cditer.free.core.enums.ModelStatus");
            if(modelStatusClass!=null){
                binder.registerCustomEditor(modelStatusClass,new ModelStatusEditor());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
