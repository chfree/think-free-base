package com.tennetcn.free.web.filter.checkhelper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface ICheckHelper {
    boolean chekcMac(ServletRequest request, ServletResponse response) throws ServletException;
}
