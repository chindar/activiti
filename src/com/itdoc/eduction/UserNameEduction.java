package com.itdoc.eduction;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据用户名推导
 */
public class UserNameEduction {

    /**
     * 根据用户——>当前用户正在执行的任务——>当前正在执行的任务的piid——>该任务的所在流程实例
     * @param assignee
     * @return
     */
    public List<ProcessInstance> getProcessInstanceByUser(String assignee) {

        List<ProcessInstance> processInstanceList = new ArrayList<>();

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 用户正在执行的任务
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        for (Task task : taskList) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = processEngine.getRuntimeService()
                    .createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();

            processInstanceList.add(processInstance);
        }
        return processInstanceList;
    }

    /**
     * 根据用户——>当前用户正在执行的任务——>当前正在执行任务的pdid——>该进程的实例ProcessDefinition
     * @param assignee
     * @return
     */
    public List<ProcessDefinition> getProcessDefinition(String assignee) {

        List<ProcessDefinition> processDefinitionList = new ArrayList<ProcessDefinition>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        for (Task task : taskList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                    .createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            processDefinitionList.add(processDefinition);
        }
        return processDefinitionList;
    }

    /**
     * 根据用户——当前用户正在执行的任务——>当前正在执行任务的pdid——>该进程的实例ProcessDefinitionEntity    丨
     *                                                          丨——>当前正咋执行任务的piid——>该任务的所在流程实例                          丨——>正在执行的ActivityImpl节点
     * @param assignee
     * @return
     */
    @Test
    public ActivityImpl getActivityImpl(String assignee) {
        ActivityImpl activity = null;

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        for (Task task : taskList) {
            String processDefinitionId = task.getProcessDefinitionId();
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = processEngine.getRuntimeService()
                    .createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();

            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                    .createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();

            activity = processDefinition.findActivity(processInstance.getActivityId());

        }
            return activity;
    }
}
