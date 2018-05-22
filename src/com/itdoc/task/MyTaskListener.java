package com.itdoc.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = (String) delegateTask.getVariable("manager");
        delegateTask.setAssignee(assignee);
//        delegateTask.setAssignee("狗蛋爹");
    }
}
