package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.TaskCompleteListener;

import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 */
public interface UserTaskCompleteDefine {

    public void completeTask();
    public boolean matchEventSource(String eventSource);
    public void fillEventSource(Set<String>  eventSourceSet);
}
