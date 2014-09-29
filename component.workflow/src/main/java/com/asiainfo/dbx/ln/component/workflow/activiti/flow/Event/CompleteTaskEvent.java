package com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event;

/**
 * 任务完成事件
 * Created by yanlei on 2014/7/24.
 */
public class CompleteTaskEvent extends   FlowEvent{
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompleteTaskEvent{");
        sb.append("eventType='").append(eventType).append('\'');
        sb.append(", eventSource='").append(eventSource).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
