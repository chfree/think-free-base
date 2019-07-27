package com.tennetcn.free.web.webapi;

import com.tennetcn.free.web.base.DateEditor;
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

    private String bindDateFormat = "yyyy-MM-dd";

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}
