package com.itdoc.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TaskTest1 {

    @Test
    public void deploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("qingjia1.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     *      设置流程一个变量
     */
    @Test
    public void startProcessInstance() {
        // 为流程变量赋值, 给<userTask id="请假申请" name="请假申请" activiti:assignee="#{userId}"></userTask>的userId赋值
        Map<String, Object> var = new HashMap<>();
        var.put("userId", "狗蛋儿");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("qingjia1:1:704", var);
    }

    /**
     * 完成请假申请任务并为部门经理审批节点赋值任务的执行人
     */
    @Test
    public void finishTaskSetManager() {
        Map<String, Object> var = new HashMap<String, Object>();
        var.put("manager", "狗蛋儿他爹");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("805", var);
    }

    /**
     * 完成部门经理审批任务并为总经理节点赋值任务执行人
     */
    @Test
    public void finishTaskSetBoss() {
        Map<String, Object> var = new HashMap<String, Object>();
        var.put("boss", "狗蛋儿他爹的爹");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("903", var);
    }

    /**
     * 结束任务
     */
    @Test
    public void endTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("1003");
    }
}
