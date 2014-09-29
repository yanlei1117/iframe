package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder;

/**
 * 查询当前正在处理任务
 * Created by yanlei on 2014/7/15.
 */
public interface TaskFinder {
    public String findCurrentTaskId() throws Exception;
}
