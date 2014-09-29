package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.candidategroup;

/**
 * 获取任务的候选组
 * Created by yanlei on 2014/7/14.
 */
public interface TaskCreateCandidateGroupDefine {
    public String getCandidateGroup(String newTaskId,String  newTaskName,String businessId,String currentUserId);
}
