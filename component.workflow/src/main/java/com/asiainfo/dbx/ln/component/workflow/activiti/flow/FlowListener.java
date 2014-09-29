package com.asiainfo.dbx.ln.component.workflow.activiti.flow;

import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.FlowEvent;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.task.UserTaskDefine;

import java.util.List;

/**
 * Created by yanlei on 2014/7/24.
 */
public class FlowListener  {

    String flowName;

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    private FlowEvent triggerEvent;

    public FlowEvent getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(FlowEvent triggerEvent) {
        this.triggerEvent = triggerEvent;
    }
    private UserTaskDefine sourceTask;
    private List<UserTaskDefine> targetTasks;

    public UserTaskDefine getSourceTask() {
        return sourceTask;
    }

    public void setSourceTask(UserTaskDefine sourceTask) {
        this.sourceTask = sourceTask;
    }

    public List<UserTaskDefine> getTargetTasks() {
        return targetTasks;
    }

    public void setTargetTasks(List<UserTaskDefine> targetTasks) {
        this.targetTasks = targetTasks;
    }



}
