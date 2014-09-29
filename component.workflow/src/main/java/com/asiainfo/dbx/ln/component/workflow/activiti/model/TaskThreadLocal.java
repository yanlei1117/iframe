package com.asiainfo.dbx.ln.component.workflow.activiti.model;

import org.activiti.engine.delegate.DelegateTask;

/**
 * Created by yanlei on 2014/7/29.
 */
public interface TaskThreadLocal {
    public void setCurrentTaskId(String taskId);
}
