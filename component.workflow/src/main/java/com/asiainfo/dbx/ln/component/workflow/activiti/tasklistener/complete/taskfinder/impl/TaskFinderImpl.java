package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.impl;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.TaskFinder;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 根据taskId和businesskey表达式获取taskId
 * Created by yanlei on 2014/7/21.
 */
public class TaskFinderImpl implements TaskFinder {
    private final Logger logger = LoggerFactory.getLogger(TaskFinderImpl.class);
    String businessKey;
    String taskId;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }



    @Override
    public String findCurrentTaskId() throws Exception {
       TaskNode  taskNode = null;
        if(AppValidationUtils.notNull(this.getTaskId())){
         taskNode =  this.getProcessFlow().findTaskByTaskId(WorkFlowCommonUtils.getVarToString(this.getTaskId()));

        }else if(AppValidationUtils.notNull(this.getBusinessKey())){
            String businessKeyValue = WorkFlowCommonUtils.getVarToString(this.getBusinessKey());
            List<TaskNode> taskNodeList =  this.getProcessFlow().findTaskByBusinessKey(businessKeyValue);
            if(AppValidationUtils.notNull(taskNodeList)) {
                if (taskNodeList.size() == 0) {
                    taskNode=  taskNodeList.get(0);
                }
            }
        }
        if(AppValidationUtils.notNull(taskNode)){
            return taskNode.getTaskId();
        }else{
            return null;
        }
    }
}
