package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.assigneeuser.TaskCreateAssigneeUserDefine;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.businesskey.TaskCreateBusinessKeyDefine;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.candidategroup.TaskCreateCandidateGroupDefine;
import com.asiainfo.dbx.ln.component.workflow.activiti.currentuser.CurrentUserFinder;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.parameter.TaskCreateParameterDefine;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 任务创建时被调用，用于设置任务的预置参数、任务的候选组、任务的受理人等。
 * Created by yanlei on 2014/7/14.
 */
public class TaskCreateListenerImpl implements TaskCreateListener {
    private final Logger logger = LoggerFactory.getLogger(TaskCreateListenerImpl.class);
    private TaskCreateParameterDefine taskCreateParameterDefine;

    private TaskCreateAssigneeUserDefine taskCreateAssigneeUserDefine;
    private TaskCreateCandidateGroupDefine taskCreateCandidateGroupDefine;

    private TaskCreateBusinessKeyDefine taskCreateBusinessKeyDefine;

    private String candidateGroupId;
    private String assigneeUserId;

    private String bussinessKey;

    public String getBussinessKey() {
        return bussinessKey;
    }

    public void setBussinessKey(String bussinessKey) {
        this.bussinessKey = bussinessKey;
    }

    @Resource(name="processFlow")
    private ProcessFlow processFlow;

   private String autoComplete = null;

    public String getAutoComplete() {
        return this.autoComplete;
    }

    public void setAutoComplete(String autoComplete) {
        this.autoComplete = autoComplete;
    }

    Map<String,Object> taskVariableMap ;

    public Map<String, Object> getTaskVariableMap() {
        return taskVariableMap;
    }

    public void setTaskVariableMap(Map<String, Object> taskVariableMap) {
        this.taskVariableMap = taskVariableMap;
    }

    public TaskCreateParameterDefine getTaskCreateParameterDefine() {
        return taskCreateParameterDefine;
    }

    public void setTaskCreateParameterDefine(TaskCreateParameterDefine taskCreateParameterDefine) {
        this.taskCreateParameterDefine = taskCreateParameterDefine;
    }

    public TaskCreateCandidateGroupDefine getTaskCreateCandidateGroupDefine() {
        return taskCreateCandidateGroupDefine;
    }

    public void setTaskCreateCandidateGroupDefine(TaskCreateCandidateGroupDefine taskCreateCandidateGroupDefine) {
        this.taskCreateCandidateGroupDefine = taskCreateCandidateGroupDefine;
    }

    public TaskCreateAssigneeUserDefine getTaskCreateAssigneeUserDefine() {
        return taskCreateAssigneeUserDefine;
    }

    public void setTaskCreateAssigneeUserDefine(TaskCreateAssigneeUserDefine taskCreateAssigneeUserDefine) {
        this.taskCreateAssigneeUserDefine = taskCreateAssigneeUserDefine;
    }



    public String getCandidateGroupId() {
        return candidateGroupId;
    }

    public void setCandidateGroupId(String candidateGroupId) {
        this.candidateGroupId = candidateGroupId;
    }

    public String getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(String assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }






    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    public TaskCreateBusinessKeyDefine getTaskCreateBusinessKeyDefine() {
        return taskCreateBusinessKeyDefine;
    }

    public void setTaskCreateBusinessKeyDefine(TaskCreateBusinessKeyDefine taskCreateBusinessKeyDefine) {
        this.taskCreateBusinessKeyDefine = taskCreateBusinessKeyDefine;
    }

    private String moduleId;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public void createTask(DelegateTask delegateTask) {

        try {
            logger.info("Tastk create! Task:{}-----------------------------------------------------",delegateTask);
            String currentUserId= this.getProcessFlow().getCurrentUserId();


            String bussinessId = null;
            if(this.getTaskCreateBusinessKeyDefine() != null){
                bussinessId =  this.getTaskCreateBusinessKeyDefine().getBusinessid(delegateTask.getId(),delegateTask.getName(),currentUserId);
            }else if(AppValidationUtils.notNull(this.getBussinessKey())){
                bussinessId = WorkFlowCommonUtils.getVarToString(this.getBussinessKey());
            }
            if(AppValidationUtils.notNull(bussinessId)){
                this.getProcessFlow().setBussinessKey(delegateTask.getId(),bussinessId);
            }

            //设置模块ID
            if(AppValidationUtils.notNull(this.getModuleId())){
                String taskModuleId =  WorkFlowCommonUtils.getVarToString(this.getModuleId());
                if(AppValidationUtils.notNull(taskModuleId)){
                    this.getProcessFlow().setModuleId(delegateTask.getId(),taskModuleId);
                }
            }
            Map<String,Object> realVarMap = null;//设置预置参数
            if(this.getTaskCreateParameterDefine() != null){
                realVarMap = this.getTaskCreateParameterDefine().getParameterMap(delegateTask.getId(),delegateTask.getName(), bussinessId, currentUserId);
            }else if(AppValidationUtils.notNull(this.getTaskVariableMap())){
                realVarMap = WorkFlowCommonUtils.convertVarMap(this.getTaskVariableMap());
            }
            if(AppValidationUtils.notNull(realVarMap)){
                this.getProcessFlow().setTaskVariables(delegateTask.getId(),realVarMap);
            }

            String candidateGroupId = null;//设置候选组
            if(this.getTaskCreateCandidateGroupDefine() != null){
                 candidateGroupId = this.getTaskCreateCandidateGroupDefine().getCandidateGroup(delegateTask.getId(),delegateTask.getName(),bussinessId,currentUserId);
            }else if(AppValidationUtils.notNull(this.getCandidateGroupId())){
                candidateGroupId = WorkFlowCommonUtils.getVarToString(this.getCandidateGroupId());
            }

            if(AppValidationUtils.notNull(candidateGroupId)){
                this.getProcessFlow().addCandidateGroupToTask(delegateTask.getId(),candidateGroupId);
            }

            //设置受理用户
            String assigneeUserId = null;
            if(AppValidationUtils.notNull(this.getTaskCreateAssigneeUserDefine())){
                assigneeUserId = this.getTaskCreateAssigneeUserDefine().getAssigneeUser(delegateTask.getId(), bussinessId,currentUserId);
            }else if(AppValidationUtils.notNull(this.getAssigneeUserId() )){
                assigneeUserId = WorkFlowCommonUtils.getAssigneeUserId(this.getProcessFlow(), this.getAssigneeUserId());
            }

            if(AppValidationUtils.notNull(assigneeUserId)){
                this.getProcessFlow().setAssigneeUserToTask(delegateTask.getId(),assigneeUserId);
            }


            if(AppValidationUtils.notNull(this.getAutoComplete())){
               String complete = WorkFlowCommonUtils.getVarToString(this.getAutoComplete());
                if(Boolean.valueOf(complete)){
                    this.getProcessFlow().completeTask(delegateTask.getId());
                }

            }
            logger.info("Tastk create finish! Task:{}-----------------------------------------------------\n",delegateTask);
        } catch (Exception e) {
            logger.error("",e);
        }

    }

}
