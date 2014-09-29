package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.TaskCreateListener;
import org.activiti.engine.delegate.DelegateTask;

/**
 * Created by yanlei on 2014/7/24.
 */
public class UserTaskCreateDefineImpl implements  UserTaskCreateDefine {
    private TaskCreateListener create;
    public TaskCreateListener getCreate() {
        return create;
    }

    public void setCreate(TaskCreateListener create) {
        this.create = create;
    }

    private String createEventSource;

    public String getCreateEventSource() {
        return createEventSource;
    }

    public void setCreateEventSource(String createEventSource) {
        this.createEventSource = createEventSource;
    }

    public void createTask(DelegateTask delegateTask) {
        this.getCreate().createTask(delegateTask);
    }
    public boolean matchEventSource(String eventSource){
        return AppStringUtils.matchPartString(this.getCreateEventSource(), eventSource);
    }
}
