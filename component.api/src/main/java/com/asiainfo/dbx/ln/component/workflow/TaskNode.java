package com.asiainfo.dbx.ln.component.workflow;

import java.util.List;
import java.util.Map;

/**
 * 流程节点
 * Created by yanlei on 2014/7/14.
 */
public class TaskNode {
       String taskId;
       String taskName;
       String moduleId;
       String businessKey;
       String executionId;
       String assignee;
       //String parentTaskId;
       String candidateGroupId;

   private List<TaskNode> completeTaskListInExecution;

    public List<TaskNode> getCompleteTaskListInExecution() {
        return completeTaskListInExecution;
    }

    public void setCompleteTaskListInExecution(List<TaskNode> completeTaskListInExecution) {
        this.completeTaskListInExecution = completeTaskListInExecution;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCandidateGroupId() {
        return candidateGroupId;
    }

    public void setCandidateGroupId(String candidateGroupId) {
        this.candidateGroupId = candidateGroupId;
    }

    Map<String,Object> variablesMap = null;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Map<String, Object> getVariablesMap() {
        return variablesMap;
    }

    public void setVariablesMap(Map<String, Object> variablesMap) {
        this.variablesMap = variablesMap;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskNode{");
        sb.append("taskId='").append(taskId).append('\'');
        sb.append(", taskName='").append(taskName).append('\'');
        sb.append(", moduleId='").append(moduleId).append('\'');
        sb.append(", businessKey='").append(businessKey).append('\'');
        sb.append(", executionId='").append(executionId).append('\'');
        sb.append(", assignee='").append(assignee).append('\'');
        sb.append(", candidateGroupId='").append(candidateGroupId).append('\'');
        sb.append(", variablesMap=").append(variablesMap);
        sb.append('}');
        return sb.toString();
    }
}
