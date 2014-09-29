package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.impl;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.TaskFinder;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 由taskId查询任务
 * Created by yanlei on 2014/7/15.
 */
public class TaskFinderByTaskId implements TaskFinder {
    private final Logger logger = LoggerFactory.getLogger(TaskFinderByTaskId.class);
    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    @Override
    public String findCurrentTaskId() throws Exception{

        TaskNode taskNode =  this.getProcessFlow().findTaskByTaskId(WorkFlowCommonUtils.getVarToString(this.getTaskId()));
        if(AppValidationUtils.notNull(taskNode)){
            return taskNode.getTaskId();
        }else{
            logger.error("TaskMatcherByTaskId :have no task for current taskId:{}"+this.getTaskId());
            return null;
        }
    }
}
