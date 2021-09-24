package com.cditer.free.login.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-24 12:12
 * @comment
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/loginweb/server/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginServerApi {
    @GetMapping("ping")
    public String ping(){
        return "pong";
    }
}
