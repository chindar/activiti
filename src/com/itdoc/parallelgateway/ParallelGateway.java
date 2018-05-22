package com.itdoc.parallelgateway;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;


public class ParallelGateway {

    /**
     * 部署流程实例
     */
    @Test
    public void deploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("parallelgateway.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("parallelGateway:1:504");
    }

    /**
     * 根据实例key查询任务
     */
    @Test
    public void getTaskByProcessdefinitionKey() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .processDefinitionKey("parallelGateway")
                .list();

        for (Task task : taskList) {
            System.out.println(task.getId() + " -- " + task.getName());
        }
    }

    /**
     * 根据piid查询任务
     */
    @Test
    public void getTaskByProcessInstanceId() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .processInstanceId("601")
                .list();

        for (Task task : taskList) {
            System.out.println(task.getId() + " -- " + task.getName());
        }
    }

    /**
     * 根据executionId查询任务
     */
    @Test
    public void getTaskByExecutionId() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .executionId("605")
                .singleResult();
        System.out.println(task.getId() + " --- " + task.getName());
    }


}
