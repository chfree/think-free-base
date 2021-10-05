package com.cditer.free.coreweb.test.apis;

import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.core.util.JsonUtils;
import com.cditer.free.coreweb.test.model.Dept;
import com.cditer.free.coreweb.test.model.UserResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-15 22:41
 * @comment
 */

@RestController
@RequestMapping(value = "/api/v1/user/")
public class UserApi {

    @PostMapping("index")
    public String index(String name){
        BaseResponse resp = new BaseResponse();
        resp.put("name", name);

        return JsonUtils.toJson(resp);
    }

    @PostMapping("indexResp")
    public BaseResponse indexResp(String name){
        UserResp resp = new UserResp();
        resp.setName(name);

        return resp;
    }

    @PostMapping("indexComplexResp")
    public BaseResponse indexComplexResp(String name,String deptName){
        UserResp resp = new UserResp();
        resp.setName(name);

        Dept dept = new Dept();
        dept.setName(deptName);
        resp.setDept(dept);

        return resp;
    }

    @PostMapping("errorIndex")
    public String errorIndex(){
        BaseResponse resp = new BaseResponse();
        resp.setStatus(1999);
        resp.setMessage("errorIndex");

        return JsonUtils.toJson(resp);
    }

    @PostMapping("errorIndexResp")
    public BaseResponse errorIndexResp(){
        BaseResponse resp = new BaseResponse();
        resp.setStatus(1999);
        resp.setMessage("errorIndex");

        return resp;
    }
}
