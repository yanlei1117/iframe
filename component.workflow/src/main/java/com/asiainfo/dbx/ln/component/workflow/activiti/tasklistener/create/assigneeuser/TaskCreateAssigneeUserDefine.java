package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.assigneeuser;

/**
 *  获取任务的受理人
 * Created by yanlei on 2014/7/14.
 */
public interface TaskCreateAssigneeUserDefine {

    public String getAssigneeUser(String taskId,String businessId,String currentUserId);
}
