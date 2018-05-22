package com.itdoc.pi;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * pi == process instance 流程实例
 * 1.启动流程实例
 * 2.完成任务
 * 3.查询各种任务
 * 4.流程实例的查询
 */
public class PiTest {

    /**
     * 涉及到的表
     * act_hi_actinst
     * 1、说明
     * act:activiti
     * hi:history
     * actinst:activity instance
     * 流程图上出现的每一个元素都称为activity
     * 流程图上正在执行的元素或者已经执行完成的元素称为activity instance
     * 2、字段
     * proc_def_id:pdid
     * proc_inst_id:流程实例ID
     * execution_id_:执行ID
     * act_id_:activity
     * act_name
     * act_type
     * act_hi_procinst
     * 1、说明
     * procinst:process instance  历史的流程实例
     * 正在执行的流程实例也在这张表中
     * 如果end_time_为null，说明正在执行，如果有值，说明该流程实例已经结束了
     * act_hi_taskinst
     * 1、说明
     * taskinst:task instance  历史任务
     * 正在执行的任务也在这张表中
     * 如果end_time_为null,说明该任务正在执行
     * 如果end_time不为null,说明该任务已经执行完毕了
     * act_ru_execution
     * 1、说明
     * ru:runtime
     * 代表正在执行的流程实例表
     * 如果当期正在执行的流程实例结束以后，该行在这张表中就被删除掉了，所以该表也是一个临时表
     * 2、字段
     * proc_inst_id_:piid  流程实例ID，如果不存在并发的情况下，piid和executionID是一样的
     * act_id:当前正在执行的流程实例(如果不考虑并发的情况)的正在执行的activity有一个，所以act_id就是当前正在执行的流程实例的正在执行的
     * 节点
     * act_ru_task
     * 1、说明
     * 代表正在执行的任务表
     * 该表是一个临时表，如果当前任务被完成以后，任务在这张表中就被删除掉了
     * 2、字段
     * id_:  主键    任务ID
     * execution_id_:执行ID
     * 根据该ID查询出来的任务肯定是一个
     * proc_inst_id:piid
     * 根据该id查询出来的任务
     * 如果没有并发，则是一个
     * 如果有并发，则是多个
     * name_:任务的名称
     * assignee_:任务的执行人
     */



    /**
     * 根据Pdid启动流程实例
     */
    @Test
    public void startProcessInstanceByPdid() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("qingjia:2:104");
    }

    /**
     * 根据PdKey启动流程实例, 默认启动最高版本
     */
    @Test
    public void startProcessInstanceByPdKey() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceByKey("qingjia");
    }

    /**
     * 完成任务
     */
    @Test
    public void finishTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("304");
    }

    /**
     * 根据任务执行人查询正在执行的任务
     */
    @Test
    public void queryTaskByAssignee() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .orderByTaskCreateTime()
                .desc()
                .taskAssignee("范冰冰经纪人")
                .list();

        for (Task task : taskList) {
            System.out.println(task.getId() + " -- " + task.getName() + " -- " + task.getAssignee());
        }
    }

    /**
     * 查询所有正在执行的任务
     */
    @Test
    public void queryAllTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .list();
        for (Task task : taskList) {
            System.out.println(task.getId() + " -- " + task.getName() + " -- " + task.getAssignee());
        }
    }

    /**
     * 根据piid查询执行的任务
     * 若是不存在并发, proc_inst_id和execution_id相同
     */
    @Test
    public void queryTaskByPiid() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .executionId("301")
                .list();
        // 因为不存在并发, 所以只存在一条
        for (Task task : taskList) {
            System.out.println(task.getId() + " -- " + task.getName() + " -- " + task.getAssignee());
        }
    }

    /**
     * 根据piid查询当前正在执行的流程实例的正在活动的节点
     */
    @Test
    public void queryActivityByPiid() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId("301")
                .singleResult();
        // 当前流程实例正在执行的activity
        String activityId = processInstance.getActivityId();
        System.out.println("activityId： " + activityId);
    }

    /**
     * 查询历史任务
     */
    @Test
    public void queryHistory() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<HistoricTaskInstance> historicTaskInstanceList = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskDeleteReason("complated")
                .list();
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            System.out.println(historicTaskInstance.getId() + " -- " + historicTaskInstance.getName());
        }
    }

}
