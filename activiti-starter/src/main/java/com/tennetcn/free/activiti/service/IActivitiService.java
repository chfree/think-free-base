package com.tennetcn.free.activiti.service;

import com.tennetcn.free.activiti.message.DeployModel;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

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
    ProcessInstance startProcess();

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
}
