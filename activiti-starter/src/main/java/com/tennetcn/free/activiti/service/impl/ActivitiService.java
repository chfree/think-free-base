package com.tennetcn.free.activiti.service.impl;

import com.tennetcn.free.activiti.enums.FlowVariableKey;
import com.tennetcn.free.activiti.exception.DeployRuntimeExcetion;
import com.tennetcn.free.activiti.message.DeployModel;
import com.tennetcn.free.activiti.message.DeployResource;
import com.tennetcn.free.activiti.message.StartProcessModel;
import com.tennetcn.free.activiti.service.IActivitiService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

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
    public ProcessInstance startProcess(StartProcessModel startProcessModel) {

        Map<String,Object> var= new HashMap<>();
        var.put(FlowVariableKey.ASSIGNEE,startProcessModel.getAssignee());
        var.put(FlowVariableKey.CAN_USERS,startProcessModel.getCanUsers());
        var.put(FlowVariableKey.CAN_GROUPS,startProcessModel.getCanGroups());

        return runtimeService.startProcessInstanceByKey(startProcessModel.getKey(),startProcessModel.getBusinessId(),var);
    }

    @Override
    public void completeProcess(String taskId) {

    }

    @Override
    public void claimProcess(String taskId, String userId) {

    }

    @Override
    public InputStream getProcessDefinitionImage(String deploymentId) {
        //获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if(list!=null && list.size()>0){
            for(String name:list){
                if(name.indexOf(".png")>=0){
                    resourceName = name;
                }
            }
        }
        return repositoryService.getResourceAsStream(deploymentId,resourceName);
    }

    @Override
    public InputStream getProcessInstanceImage(String taskId) {
        Task task= taskService.createTaskQuery().taskId(taskId).singleResult();
        String pdId = task.getProcessDefinitionId();
        BpmnModel bpmnModel= repositoryService.getBpmnModel(pdId);

        //定义使用宋体
        String fontNam = "黑体";
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(task.getExecutionId());

        DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();
        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
        List<String> hightLightElements = new ArrayList<>();
        return gen.generateDiagram(bpmnModel, "png", activeActivityIds, hightLightElements,
                fontNam, fontNam, fontNam, null, 1.0);
    }
}
