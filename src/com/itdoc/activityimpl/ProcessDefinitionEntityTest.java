package com.itdoc.activityimpl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.List;

/**
 * 获取进程实例
 */
public class ProcessDefinitionEntityTest {

    /**
     * 根据pdid获取ProcessDefinitionEntity
     */
    @Test
    public void getProcessdefinitonEntity()  {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .getProcessDefinition("qingjia:2:104");
        System.out.println(processDefinition);
    }

    /**
     * 根据pdid获取ProcessDefinitionEntity中的activityImpl
     */
    @Test
    public void getActivityImpl() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .getProcessDefinition("qingjia:2:104");

        // ActivityImpl是一个对象, 一个ActivityImpl代表ProcessDefinitionEntity中的一个节点
        List<ActivityImpl> activityList = processDefinition.getActivities();
        for (ActivityImpl activity : activityList) {
            System.out.println(activity.getId());
            System.out.print("height: " + activity.getHeight());
            System.out.print("width: " + activity.getWidth());
            System.out.print("x: " + activity.getX());
            System.out.println("y: " + activity.getY());
        }

    }


    /**
     * 获取ProcessDefinitionEntity中所有的ActivityImpl的所有的PvmTransition
     */
    @Test
    public void getSequenceFlow() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .getProcessDefinition("qingjia:2:104");

        // 获取ProcessDefinitionEntity中的所有ActivityImpl节点
        List<ActivityImpl> activityList = processDefinition.getActivities();

        for (ActivityImpl activity : activityList) {
            System.out.println(activity.getId());
            System.out.print("height: " + activity.getHeight());
            System.out.print("width: " + activity.getWidth());
            System.out.print("x: " + activity.getX());
            System.out.println("y: " + activity.getY());

            // 获取每个ActivityImpl节点中的SequenceFlow出口
            List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();

            for (PvmTransition pvmTransition : pvmTransitionList) {
                System.out.println("sequenceFlowId: " + pvmTransition.getId());
            }
        }
    }


    /**
     * 获取当前正在执行流程实例的activityImpl——>PvmTransition
     */
    @Test
    public void getCurrentActivityImpl() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 根据pdid获取ProcessDefinitionEntity实例
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .getProcessDefinition("qingjia:2:104");

        // 根据piid获取流程实例
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId("301")
                .singleResult();

        // 根据流程实例获取当前执行activityImpl节点id进而获取当前ActivityImpl节点
        ActivityImpl activity = processDefinition.findActivity(processInstance.getActivityId());

        // 流程实例id
        System.out.println(processInstance.getId());
        System.out.println(activity.getId());
        System.out.print("height: " + activity.getHeight() + " ");
        System.out.print("width: " + activity.getWidth() + " ");
        System.out.print("x: " + activity.getX() + " ");
        System.out.println("y: " + activity.getY());
    }

}
