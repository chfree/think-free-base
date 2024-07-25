package com.cditer.free.quartz.demo.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-24 21:28
 * @comment
 */

@RestController
@RequestMapping("/home/")
public class HomeApi{

    @RequestMapping("index")
    public String index() {
        return "hello world";
    }
}
