package com.tennetcn.free.web.demo.apis;

import com.tennetcn.free.web.webapi.FirstApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/home")
public class HomeApi extends FirstApi {

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }
}
