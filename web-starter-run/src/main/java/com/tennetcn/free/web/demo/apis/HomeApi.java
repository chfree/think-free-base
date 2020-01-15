package com.tennetcn.free.web.demo.apis;

import cn.hutool.json.JSONUtil;
import com.tennetcn.free.web.demo.viewmodel.User;
import com.tennetcn.free.web.webapi.FirstApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/home")
public class HomeApi extends FirstApi {

    @GetMapping(value = "/hello")
    public String hello(){
        servletResponse.setHeader("aaa","xxx");
        return "hello";
    }

    @GetMapping(value = "/testUser")
    public String testUser(@Valid User user){
        return JSONUtil.toJsonStr(user);
    }
}
