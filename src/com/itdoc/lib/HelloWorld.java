package com.itdoc.lib;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 1.部署流程
 * 2.启动流程实例
 * 3.请假人发出请假申请
 * 4.经纪人查看任务
 * 5.经纪人审批
 * 6.最终boss审批
 */
public class HelloWorld {


    /**
     * 部署流程
     */
    @Test
    public void testDeploy() {
        // 获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("qingjia.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("qingjia:1:4");
    }

    /**
     * 请假人完成请假申请
     */
    @Test
    public void finishApply() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("104");
    }

    /**
     * 范冰冰经纪人查询当前执行任务
     */
    @Test
    public void getManagerTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("范冰冰经纪人")
                .list();
        for (Task task: taskList) {
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }

    /**
     * 部门经理审批完成
     */
    @Test
    public void finishManagerTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("202");
    }

    /**
     * 总经理完成审批
     */
    @Test
    public void finishBossTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("302");
    }
}
