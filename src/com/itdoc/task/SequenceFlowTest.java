package com.itdoc.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SequenceFlowTest {

    /**
     * 部署流程
     */
    @Test
    public void deploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("sequenceFlow.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("qingjia4:2:404");
    }

    /**
     * 完成请假申请并设置条件变量为2天
     */
    @Test
    public void finishTaskApply() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Map<String, Object> var = new HashMap<String, Object>();
//        var.put("days", 2);
        var.put("days", 4);
        processEngine.getTaskService()
                .complete("504", var);
    }

    /**
     * 部门经理审批完成
     */
    @Test
    public void finishTaskManager() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("603");
    }

    /**
     * 因为判断条件为4天, 会触发总经理审批任务, 总经理审批任务完成
     */
    @Test
    public void finishTaskBoss() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("702");
    }
}
