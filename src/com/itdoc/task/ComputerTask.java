package com.itdoc.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class ComputerTask {

    /**
     * 部署流程
     */
    @Test
    public void deploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("repair.bpmn")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("computerRepair:1:4");
    }

    /**
     * 根据任务Id查询候选人
     */
    @Test
    public void queryCondidateByTaskId() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<IdentityLink> identityLinkList = processEngine.getTaskService()
                .getIdentityLinksForTask("104");

        for (IdentityLink identityLink : identityLinkList) {
            System.out.println(identityLink.getUserId());
        }
    }

    /**
     * 根据流程实例piid查询候选人
     */
    @Test
    public void queryCondidateByProcessInstanceId() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<IdentityLink> identityLinkList = processEngine.getRuntimeService()
                .getIdentityLinksForProcessInstance("101");

        for (IdentityLink identityLink : identityLinkList) {
            System.out.println(identityLink.getUserId());
        }
    }

    /**
     * 根据候选人查询组任务
     */
    @Test
    public void queryTaskByCandidate() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskCandidateUser("工程师1")
                .list();
        for (Task task : taskList) {
            System.out.println(task.getName());
            System.out.println(task.getId());
        }
    }

    /**
     *  设置正在执行的任务的执行人
     */
    @Test
    public void setCurrentAssingee() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .setAssignee("104", "电脑维修");
    }

    /**
     * 候选人任选一个认领任务
     */
    @Test
    public void claimTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .claim("104", "工程师1");
    }

    /**
     * 结束流程实例
     */
    @Test
    public void endProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("104");
    }

}
