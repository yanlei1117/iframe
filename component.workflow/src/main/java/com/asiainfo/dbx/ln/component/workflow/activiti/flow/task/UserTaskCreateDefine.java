package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.TaskCreateListener;
import org.activiti.engine.delegate.DelegateTask;

import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 */
public interface UserTaskCreateDefine extends  UserTaskFireCreate{
    public boolean matchEventSource(String eventSource);

}
