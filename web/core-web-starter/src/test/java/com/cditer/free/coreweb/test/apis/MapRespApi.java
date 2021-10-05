package com.cditer.free.coreweb.test.apis;

import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.coreweb.test.model.Dept;
import com.cditer.free.coreweb.test.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/v1/mapResp/")
public class MapRespApi {

    @PostMapping("index")
    public BaseResponse index(String name, String deptName){
        BaseResponse resp = new BaseResponse();

        User user = new User();
        user.setName(name);

        Dept dept = new Dept();
        dept.setName(deptName);
        user.setDept(dept);

        resp.put("user", user);

        return resp;
    }

    @PostMapping("indexList")
    public BaseResponse indexList(String name, String deptName){
        BaseResponse resp = new BaseResponse();

        resp.put("names", Arrays.asList(name, deptName));

        return resp;
    }

    @PostMapping("indexUserList")
    public BaseResponse indexUserList(String name, String deptName){
        BaseResponse resp = new BaseResponse();

        User user = new User();
        user.setName(name);

        Dept dept = new Dept();
        dept.setName(deptName);
        user.setDept(dept);

        resp.put("users", Arrays.asList(user));

        return resp;
    }
}
