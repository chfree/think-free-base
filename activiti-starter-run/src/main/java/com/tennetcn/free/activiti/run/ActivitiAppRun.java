package com.tennetcn.free.activiti.run;

import cn.hutool.json.JSONUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-09 22:58
 * @comment
 */

@SpringBootApplication
public class ActivitiAppRun implements CommandLineRunner {
    @Autowired
    RepositoryService repositoryService;

    public static void main(String[] args) {
        SpringApplication.run(ActivitiAppRun.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        List<Map<String,Object>> pdList=new ArrayList<>();
        for (ProcessDefinition pd : list) {
            Map<String,Object> pdMaps=new HashMap<>();
            pdMaps.put("pdId",pd.getId());
            pdMaps.put("pdName",pd.getName());
            pdMaps.put("pdKey",pd.getKey());
            pdMaps.put("pdDeploymentId",pd.getDeploymentId());
            pdMaps.put("pdDescription",pd.getDescription());

            pdList.add(pdMaps);
        }
        System.out.println(JSONUtil.toJsonStr(pdList));
    }
}
