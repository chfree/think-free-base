package com.cditer.free.coreweb.test.apis;

import com.cditer.free.coreweb.test.model.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-10 12:21
 * @comment
 */

@RestController
@RequestMapping(value = "/api/v1/home/")
public class HomeApi {

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @Autowired
    HttpServletRequest request;

    @PostMapping("paramIndex")
    public String paramIndex(String name){
        return name+"index";
    }

    @PostMapping("userIndex")
    public String userIndex(UserReq req, String token){
        return req.getName()+":"+req.getAge() + ":"+token;
    }

    @PostMapping("userReqBodyIndex")
    public String userReqBodyIndex(@RequestBody UserReq req){
        return req.getName()+":"+req.getAge();
    }
}
