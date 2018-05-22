package com.itdoc.receivetask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

public class ReceiveTaskTest {


    /**
     * 部署流程实例
     */
    @Test
    public void deploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("receivetask.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("receivetask:1:4");
    }

    /**
     * 完成我就是一个节点的流程
     */
    @Test
    public void finishTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("104");
    }

    /**
     * 执行操作将流程通过receive节点
     */
    @Test
    public void nextNode() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .signal("101");
    }

}
