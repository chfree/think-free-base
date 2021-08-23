package com.cditer.free.web.demo.apis;

import cn.hutool.json.JSONUtil;
import com.cditer.free.web.demo.viewmodel.User;
import com.cditer.free.web.webapi.FirstApi;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/home")
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

    @PostMapping(value = "/testDate")
    public Object testDate(@RequestBody TestDateReq req){
        return req;
    }

    @PostMapping(value = "/testList")
    public Object testList(ListReq req){
        System.out.println(JSONUtil.toJsonStr(req.getUserIds()));

        return  req.hashCode();
    }

    @PostMapping(value = "/postTest")
    public String testDate(String name){
        return "post:" + name;
    }
}
