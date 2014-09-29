package com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event;

import org.activiti.engine.delegate.DelegateTask;

import java.util.Map;

/**
 * Created by yanlei on 2014/7/24.
 */
public class FlowEvent {
    public static String CREATE_EVENT="CREATE";
    public static String COMPLEATE_EVENT="COMPLETE";


    String eventType;

    String eventSource;



    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }


    public static FlowEvent createTaskEvent(DelegateTask delegateTask,String eventSource){
        CreateTaskEvent flowEvent = new CreateTaskEvent();
        flowEvent.setEventType(FlowEvent.CREATE_EVENT);
        flowEvent.setTaskName(delegateTask.getName());
        flowEvent.setSourceObject(delegateTask);
        flowEvent.setEventSource(eventSource);
        return flowEvent;
    }
    public static FlowEvent completeTaskEvent(String eventSource){
        CompleteTaskEvent flowEvent = new CompleteTaskEvent();
        flowEvent.setEventType(FlowEvent.COMPLEATE_EVENT);
        flowEvent.setEventSource(eventSource);
        return flowEvent;
    }


}
