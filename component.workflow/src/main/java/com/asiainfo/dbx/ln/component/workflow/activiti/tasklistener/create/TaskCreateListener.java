package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create;

import org.activiti.engine.delegate.DelegateTask;

/**
 * Created by yanlei on 2014/7/18.
 */
public interface TaskCreateListener {
    public void createTask(DelegateTask delegateTask);
}
