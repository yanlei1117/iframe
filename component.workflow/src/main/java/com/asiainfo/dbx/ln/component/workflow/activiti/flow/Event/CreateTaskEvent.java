package com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event;

/**
 * Created by Administrator on 2014/7/24.
 */
public class CreateTaskEvent extends  FlowEvent{
    String taskName;

    Object sourceObject;

    public Object getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(Object sourceObject) {
        this.sourceObject = sourceObject;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateTaskEvent{");
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", eventSource='").append(eventSource).append('\'');
        sb.append("taskName='").append(taskName).append('\'');
        sb.append(", sourceObject=").append(sourceObject).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
