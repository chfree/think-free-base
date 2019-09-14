package com.tennetcn.free.activiti.service.impl;

import com.tennetcn.free.activiti.exception.DeployRuntimeExcetion;
import com.tennetcn.free.activiti.message.DeployModel;
import com.tennetcn.free.activiti.message.DeployResource;
import com.tennetcn.free.activiti.service.IActivitiService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-11 12:11
 * @comment
 */
@Component
public class ActivitiService implements IActivitiService {

    @Autowired
    RepositoryService repositoryService;

    @Override
    public Deployment deployProcess(DeployModel deployModel) {
        DeploymentBuilder builder = repositoryService.createDeployment();

        DeployResource deployResource = deployModel.getDeployResource();
        if(deployResource==null){
            throw new DeployRuntimeExcetion("流程资源相关信息不能为空");
        }

        if(!StringUtils.isEmpty(deployResource.getClasspathResource())){
            builder.addClasspathResource(deployResource.getClasspathResource());
        }
        if(!StringUtils.isEmpty(deployResource.getResourceName())){
            if(deployResource.getInputStream()!=null){
                builder.addInputStream(deployResource.getResourceName(),deployResource.getInputStream());
            }
            if(deployResource.getBytes()!=null){
                builder.addBytes(deployResource.getResourceName(),deployResource.getBytes());
            }
        }

        builder.category(deployModel.getCategory())
                .name(deployModel.getName());


        return builder.deploy();
    }

    @Override
    public ProcessInstance startProcess() {
        return null;
    }

    @Override
    public void completeProcess(String taskId) {

    }

    @Override
    public void claimProcess(String taskId, String userId) {

    }
}
