package com.tennetcn.free.web.newproject.apis;

import com.tennetcn.free.web.newproject.viewmodel.ProjectNewTemplate;
import com.tennetcn.free.web.webapi.FirstApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/projectInit/")
public class ProjectInitApi extends FirstApi {

    @PostMapping(value = "newTemplate")
    public String newTemplate(ProjectNewTemplate newTemplate){

        return "hello";
    }
}
