package com.asiainfo.dbx.ln.component.workflow.activiti;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import com.asiainfo.dbx.ln.component.workflow.activiti.currentuser.CurrentUserFinder;
import com.asiainfo.dbx.ln.component.workflow.activiti.taskconvert.TaskNodeConverter;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by yanlei on 2014/7/10.
 */
public class ProcessFlowImpl implements com.asiainfo.dbx.ln.component.workflow.ProcessFlow {
    private final Logger logger = LoggerFactory.getLogger(ProcessFlowImpl.class);
    private RuntimeService runtimeService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    private TaskService taskService = null;

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    private TaskNodeConverter TaskNodeConvert;

    public TaskNodeConverter getTaskNodeConvert() {
        return TaskNodeConvert;
    }

    public void setTaskNodeConvert(TaskNodeConverter taskNodeConvert) {
        TaskNodeConvert = taskNodeConvert;
    }

    private IdentityService identityService;

    public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    private CurrentUserFinder currentUserFinder;

    public CurrentUserFinder getCurrentUserFinder() {
        return currentUserFinder;
    }

    public void setCurrentUserFinder(CurrentUserFinder currentUserFinder) {
        this.currentUserFinder = currentUserFinder;
    }

    private HistoryService historyService;

    public HistoryService getHistoryService() {
        return historyService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    String currentUserId;

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    /**
     * 启动流程
     * @param processKey 流程KEY(流程定义bpmn文件中process 的ID属性值)
     * @return 流程ID
     */

    public String startFlowByProcessKey(String processKey){
        ProcessInstance processInstance =  this.getRuntimeService().startProcessInstanceByKey(processKey);
        return processInstance.getId();
    }

    /**
     * 完成任务
     * @param taskId 任务ID
     */
    @Override
    public void completeTask(String taskId){
        logger.info("completeTask(taskId:{})",taskId);
        this.getTaskService().complete(taskId);

    }

    /**
     * 设置任务变量
     * @param taskId
     * @param variableMap
     */
    public void setTaskVariables(String taskId,Map<String,Object> variableMap){
        this.getTaskService().setVariablesLocal(taskId,variableMap);
        logger.info("setTaskVariables(taskId:{},variableMap:{})",taskId,variableMap);
    }

    /**
     * 设置任务变量
     * @param taskId
     * @param varName
     * @param varValue
     */
    public void setTaskVariable(String taskId,String varName,Object varValue){
        this.getTaskService().setVariableLocal(taskId,varName,varValue);
        logger.info("setTaskVariable(taskId:{},varName:{},varValue:{})",taskId,varName,varValue);
    }

    public Object getTaskVariable(String taskId,String varName){

        Object value = this.getTaskService().getVariableLocal(taskId,varName);
        logger.info("getTaskVariable(taskId:{},varName:{}) =={}",taskId,varName,value);
        return value;
    }
    /**
     * 查询用户组的候选任务
     * @param groupId 用户组ID
     * @return 所有候选任务ID列表
     */
    @Override
    public List<TaskNode> findTaskByCandidateGroupId(String groupId)throws Exception{
        List<Task> taskList = this.getTaskService().createTaskQuery().taskCandidateGroup(groupId).list();
        List<TaskNode> taskNodeList = convertToTaskNodeList(taskList);
        logger.info("findTaskByCandidateGroupId(groupId:{}) =={}",groupId,taskNodeList);
        return taskNodeList;
    }

    /**
     * 查询用户的候选任务
     * @param userId 用户ID
     * @return 所有任务ID列表
     */
    @Override
    public List<TaskNode> findTaskByCandidateUserId(String userId)throws Exception{
        List<Task> taskList = this.getTaskService().createTaskQuery().taskCandidateUser(userId).list();
        List<TaskNode> taskNodeList = convertToTaskNodeList(taskList);
        logger.info("findTaskByCandidateUserId(userId:{}) =={}",userId,taskNodeList);
        return taskNodeList;
    }

    /**
     * 查询用户当前需要受理的任务
     * @param userId 用户ID
     * @return 当前所有需要受理任务ID列表
     */
    @Override
    public List<TaskNode> findTaskByAssigneeUserId(String userId)throws Exception{
        List<Task> taskList = this.getTaskService().createTaskQuery().taskAssignee(userId).list();
        List<TaskNode> taskNodeList = convertToTaskNodeList(taskList);
        logger.info("findTaskByAssigneeUserId(userId:{}) =={}",userId,taskNodeList);
        return taskNodeList;
    }

    /**
     * 指定任务的候选用户组
     * @param taskId 任务ID
     * @param groupId 候选用户组
     */
    @Override
    public void addCandidateGroupToTask(String taskId, String groupId){
        this.getTaskService().addCandidateGroup(taskId,groupId);
        logger.info("addCandidateGroupToTask(taskId:{},groupId={})",taskId,groupId);
    }

    /**
     * 指定任务的受理人
     * @param taskId 任务ID
     * @param userId    任务的受理人
     */
    @Override
    public void setAssigneeUserToTask(String taskId, String userId){
        this.getTaskService().setAssignee(taskId, userId);
        logger.info("setAssigneeUserToTask(taskId:{},userId={})",taskId,userId);
    }

    public String getCurrentUserId(){


        if(AppValidationUtils.notNull(this.currentUserId)){
            String currentUserId =  WorkFlowCommonUtils.getVarToString(this.currentUserId);
            logger.info("findCurrentUserId:currentUserIdExpresssion:{},result={}",this.currentUserId,currentUserId);
            return currentUserId;
        }
        AppAssertUtils.notNull(this.getCurrentUserFinder()," property named 'currentUserFinder' is not allow null");
        String currentId= this.getCurrentUserFinder().findCurrentUserId();
        logger.info("getCurrentUserId()={}",currentId);
        return currentId;
    }

    private List<TaskNode> convertToTaskNodeList(List<Task> tasklist) throws Exception{
        List<TaskNode> taskNodeList = null;
        if(AppValidationUtils.notEmpty(tasklist)){
            taskNodeList = new ArrayList<TaskNode>(tasklist.size());
            for(Task task:tasklist){
                TaskNode taskNode = this.getTaskNodeConvert().convert(task);

                taskNodeList.add(taskNode);
            }
        }
        return taskNodeList;
    }




    private TaskNode convertToTaskNode(Task task) throws Exception{
        //AppAssertUtils.notNull(this.getTaskNodeConvert()," please config taskNodeConvert  property for current class");
        return this.getTaskNodeConvert().convert(task);
    }

    @Override
    public void setBussinessKey(String taskId, String bussinessKey) {
        this.setTaskVariable(taskId, ProcessFlow.VAR_BUSSINESS_KEY, bussinessKey);
        logger.info("setBussinessKey(taskId:{},bussinessKey:{})",taskId,bussinessKey);
    }

    @Override
    public void setModuleId(String taskId, String moduleId) {
        this.setTaskVariable(taskId, ProcessFlow.VAR_MODULE_ID, moduleId);
        logger.info("setModuleId(taskId:{},moduleId:{})",taskId,moduleId);
    }

    @Override
    public String getModuleId(String taskId) {
        Object value =  this.getTaskVariable(taskId,ProcessFlow.VAR_MODULE_ID);
        logger.info("getModuleId(taskId:{})=={}",taskId,value);
        if(value != null){
            return value.toString();
        }else{
            return null;
        }
    }

    @Override
    public String getBussinessKey(String taskId) {

        Object value =  this.getTaskVariable(taskId,ProcessFlow.VAR_BUSSINESS_KEY);
        logger.info("getBussinessKey(taskId:{})=={}",taskId,value);
        if(value != null){
            return value.toString();
        }else{
            return null;
        }
    }

    @Override
    public List<TaskNode> findTaskByAssigneeUserTaskName(String assigneeUserId, String taskName) throws Exception{
        AppAssertUtils.notNull(assigneeUserId,"assigneeUserId is not allow null");
        TaskQuery taskQuery = this.getTaskService().createTaskQuery().taskAssignee(assigneeUserId);
        if(AppValidationUtils.notNull(taskName)){
            taskQuery.taskName(taskName);
        }
        List<TaskNode> taskList =  convertToTaskNodeList(taskQuery.list());
        logger.info("findTaskByAssigneeUserTaskName(assigneeUserId:{},taskName:{})=={}",assigneeUserId,taskName,taskList);
        return taskList;
    }
    public List<TaskNode> findTaskByCurrentUserId( ) throws Exception {
        List<TaskNode> taskNodeList =  this.findTaskByAssigneeUserId(this.getCurrentUserId());
        logger.info("findTaskByCurrentUserId()=={}",taskNodeList);
        return taskNodeList;
    }
    public List<TaskNode> findTaskByBusinessKey(String businessKey)throws Exception{
        Execution execution = this.getRuntimeService().createExecutionQuery().variableValueEquals(ProcessFlow.VAR_BUSSINESS_KEY,businessKey).singleResult();
        if(AppValidationUtils.notNull(execution)){
            String executionId = execution.getId();
            List<Task> taskList = this.getTaskService().createTaskQuery().executionId(executionId).list();
            List<TaskNode> taskNodeList = this.convertToTaskNodeList(taskList);
            logger.info("findTaskByBusinessKey(businessKey:{})=={}",businessKey,taskNodeList);
            return taskNodeList;
        }else{
            logger.info("findTaskByBusinessKey(businessKey:{})==null", businessKey);
        }
        return null;
    }

    @Override
    public TaskNode findTaskByTaskId(String taskId)throws Exception {
        Task task = this.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        if(AppValidationUtils.notNull(task)){
            TaskNode taskNode = this.convertToTaskNode(task);
            logger.info("findTaskByTaskId(taskid:{})=={}",taskNode);
            return taskNode;
        }else{
            logger.info("findTaskByTaskId(taskid:{})==null",taskId);
        }

        return null;
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> resultMap) {
        logger.info("completeTask(taskid:{},resultMap:{})",taskId,resultMap);
        this.getTaskService().complete(taskId,resultMap);

    }

    @Override
    public Map<String, Object> getTaskVariables(String taskId) throws Exception {
        Map<String,Object> variablesMap = this.getTaskService().getVariables(taskId);
        logger.info("findTaskVariables(taskId:{})={}",taskId,variablesMap);
        return variablesMap;
    }
    @Override
    public Map<String, Object> getHistoryTaskVariables(String taskId) throws Exception {
        List<HistoricVariableInstance> historicVariableInstancesList = this.getHistoryService().createHistoricVariableInstanceQuery().taskId(taskId).list();
        Map<String,Object> variablesMap = null;
        if(AppValidationUtils.notEmpty(historicVariableInstancesList)){
            variablesMap = new HashMap<String, Object>(historicVariableInstancesList.size());
            for( HistoricVariableInstance historicVariableInstance:historicVariableInstancesList){
                Object value = historicVariableInstance.getValue();
                String name = historicVariableInstance.getVariableName();
                variablesMap.put(name,value);
            }
        }


        logger.info("findTaskVariables(taskId:{})={}",taskId,variablesMap);
        return variablesMap;
    }


    public String getCandidateGroupIdByHistoryTaskId(String taskId){
       List<HistoricIdentityLink> historicIdentityLinkList =  this.getHistoryService().getHistoricIdentityLinksForTask(taskId);
        Iterator<HistoricIdentityLink> iterator = historicIdentityLinkList.iterator();
        while(iterator.hasNext()){
            HistoricIdentityLink historicIdentityLink =  iterator.next();
            if(AppValidationUtils.notNull(historicIdentityLink)){
                if(AppValidationUtils.notNull(historicIdentityLink.getType())){
                    if(historicIdentityLink.getType().equals("candidate")){
                        return historicIdentityLink.getGroupId();
                    }
                }
            }
        }
        return null;
    }
    public List<TaskNode> findCompleateTaskInExecution(String currentTaskId)throws Exception {
        TaskNode taskNode = this.findTaskByTaskId(currentTaskId);
        if(AppValidationUtils.notNull(taskNode)){
            String executionId = taskNode.getExecutionId();
            List<HistoricTaskInstance> taskList = this.getHistoryService().createHistoricTaskInstanceQuery().executionId(executionId).finished().list();
            List<TaskNode> taskNodeList = convertHistoricTaskToTaskNodeList(taskList);
            return taskNodeList;
        }
        return null;
    }

    private List<TaskNode> convertHistoricTaskToTaskNodeList(List<HistoricTaskInstance> tasklist) throws Exception{
        List<TaskNode> taskNodeList = null;
        if(AppValidationUtils.notEmpty(tasklist)){
            taskNodeList = new ArrayList<TaskNode>(tasklist.size());
            for(HistoricTaskInstance task:tasklist){
                TaskNode taskNode = this.getTaskNodeConvert().convert(task);

                taskNodeList.add(taskNode);
            }
        }
        return taskNodeList;
    }
}
