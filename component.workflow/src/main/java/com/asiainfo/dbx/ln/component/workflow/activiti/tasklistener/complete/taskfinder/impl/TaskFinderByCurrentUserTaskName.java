package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.impl;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.TaskFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 按指定任务名查询当前用户正在受理的任务
 * Created by yanlei on 2014/7/15.
 */
public class TaskFinderByCurrentUserTaskName implements TaskFinder {
    private final Logger logger = LoggerFactory.getLogger(TaskFinderByCurrentUserTaskName.class);
    private String taskName;
    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    @Override
    public String findCurrentTaskId() throws Exception{

           List<TaskNode> taskNodeList =   this.getProcessFlow().findTaskByAssigneeUserTaskName(this.getProcessFlow().getCurrentUserId(),this.getTaskName());
            if(AppValidationUtils.notNull(taskNodeList)){
                if(taskNodeList.size()==0){
                    return taskNodeList.get(0).getTaskId();
                }else{
                    logger.error("tasks  {} more than on    for current userId:{},taskName:{}"+taskNodeList,this.getProcessFlow().getCurrentUserId(),this.getTaskName());
                }
            }else{
                logger.error("have no task for current userId:{},taskName:{}"+this.getProcessFlow().getCurrentUserId(),this.getTaskName());

            }

       return null;

    }
}
