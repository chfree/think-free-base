package com.cditer.free.web.newproject.apis;

import com.cditer.free.web.webapi.FirstApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/newproject/")
public class NewProjectApi extends FirstApi {
    @GetMapping(value = "index")
    public String index(){
        return "/newproject/index.html";
    }
}
