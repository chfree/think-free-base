package com.tennetcn.free.activiti.service;

import com.tennetcn.free.activiti.message.DeployModel;
import com.tennetcn.free.activiti.message.StartProcessModel;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.InputStream;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-09-11 12:09
 * @comment
 */

public interface IActivitiService {
    /**
     * 部署一个流程
     * @return
     */
    Deployment deployProcess(DeployModel deployModel);

    /**
     * 开启一个流程
     * @return
     */
    ProcessInstance startProcess(StartProcessModel startProcessModel);

    /**
     * 完成一个流程
     * @param taskId
     */
    void completeProcess(String taskId);

    /**
     * 领取一个流程
     * @param taskId
     * @param userId
     */
    void claimProcess(String taskId,String userId);

    /**
     * 查看流程图
     * @param deploymentId
     * @return
     */
    InputStream getProcessDefinitionImage(String deploymentId);

    /**
     * 查看流程图，根据任务id
     * @param taskId
     * @return
     */
    InputStream getProcessInstanceImage(String taskId);
}
