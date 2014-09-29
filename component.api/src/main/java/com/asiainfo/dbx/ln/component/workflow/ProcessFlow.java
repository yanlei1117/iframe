package com.asiainfo.dbx.ln.component.workflow;

import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/7/14.
 */
public interface ProcessFlow {
    public static final String VAR_CURRENT_USER_ID="CURRENT_USER_ID_";
    public static final String VAR_BUSSINESS_KEY="BUSSINESS_KEY_";
    public static final String VAR_TASK_ID="TASK_ID_";
    public static final String VAR_MODULE_ID="TASK_ID_";
    public static final String VAR_CANDIDATE_GROUP_ID="CANDIDATE_GROUP_ID";


    public static final String SERVICE_METHOD_PARAMETER_VAR_NAME="SERVICE_METHOD.PARAMETERS";
    public static final String SERVICE_METHOD_RESULT_VAR_NAME="SERVICE_METHOD.RESULT";
    public void completeTask(String taskId);
    public void completeTask(String taskId,Map<String,Object> resultMap);
    public void addCandidateGroupToTask(String taskId, String groupId);
    public void setAssigneeUserToTask(String taskId, String userId);
    public String startFlowByProcessKey(String processKey);
    public String getCurrentUserId();


    public void setBussinessKey(String taskId,String bussinessKey);
    public String getBussinessKey(String taskId);

    public void setModuleId(String taskId,String moduleId);
    public String getModuleId(String taskId);

    public List<TaskNode> findTaskByCandidateGroupId(String groupId) throws Exception;
    public List<TaskNode> findTaskByCandidateUserId(String userId) throws Exception;
    public List<TaskNode> findTaskByAssigneeUserId(String userId) throws Exception;
    public List<TaskNode> findTaskByAssigneeUserTaskName(String assigneeUserId,String taskName)throws Exception;
    public  List<TaskNode>  findTaskByCurrentUserId( ) throws Exception;
    public  TaskNode  findTaskByTaskId(String taskId )throws Exception;
    public List<TaskNode> findTaskByBusinessKey(String businessKey) throws Exception;


    public void setTaskVariables(String taskId,Map<String,Object> variableMap);

    public void setTaskVariable(String taskId,String varName,Object varValue);
    public Object getTaskVariable(String taskId,String varName);

    public Map<String,Object> getTaskVariables(String taskId) throws Exception;
    public Map<String, Object> getHistoryTaskVariables(String taskId) throws Exception ;

    public List<TaskNode> findCompleateTaskInExecution(String currentTaskId)throws Exception ;
    public String getCandidateGroupIdByHistoryTaskId(String taskId);

}
