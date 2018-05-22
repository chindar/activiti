package com.itdoc.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TaskTest2 {

    /**
     * 部署流程实例
     */
    @Test
    public void deploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("qingjia2.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例并设置流程变量任务执行人
     */
    @Test
    public void startProcessInstance() {
        Map<String, Object> var = new HashMap<String, Object>();
        var.put("applicator", "狗蛋儿");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("qingjia2:1:4", var);
    }

    /**
     * 请假申请完成并利用Listener为部门经理审批节点赋值执行任务人
     */
    @Test
    public void finishTaskSetManager() {
        Map<String, Object> var = new HashMap<String, Object>();
        var.put("manager", "狗蛋儿爹");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("105");
    }

    /**
     * 完成部门经理审批
     */
    @Test
    public void finishTaskManager() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("202");
    }


    /**
     * 通过程序的方式设置正在执行任务的任务执行人
     */
    @Test
    public void setAssingee() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .setAssignee("302","狗蛋爷爷");
    }
}
