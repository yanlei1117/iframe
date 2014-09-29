package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskResult;

import java.util.Map;

/**
 * 查找任务的执行结果
 * Created by yanlei on 2014/7/16.
 */
public interface TaskResultFinder {
    public Map<String,Object> findTaskResult();
}
