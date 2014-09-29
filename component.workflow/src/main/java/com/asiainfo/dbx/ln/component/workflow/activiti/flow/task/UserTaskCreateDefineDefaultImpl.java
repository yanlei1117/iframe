package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

/**
 * Created by yanlei on 2014/7/24.
 * 忽略事件源
 * 一个UserTask 只有一种方式创建，就用这个类
 */
public class UserTaskCreateDefineDefaultImpl extends UserTaskCreateDefineImpl{
    @Override
    public boolean matchEventSource(String eventSource) {
        return true;
    }
}
