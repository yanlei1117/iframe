package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.parameter;

import java.util.Map;

/**
 * 获取任务相关信息
 * 如任务名称、任务描述等等
 * Created by yanlei on 2014/7/14.
 */
public interface TaskCreateParameterDefine {
    public Map<String,Object> getParameterMap(String newTaskId,String newTaskName,String businessId,String currentUserId);
}
