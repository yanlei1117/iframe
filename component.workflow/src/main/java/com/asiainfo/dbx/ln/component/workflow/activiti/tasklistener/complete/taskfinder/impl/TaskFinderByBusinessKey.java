package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.impl;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
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
 * 查询当前用户正在受理的任务
 * Created by yanlei on 2014/7/15.
 */
public class TaskFinderByBusinessKey implements TaskFinder {
    private Logger logger = LoggerFactory.getLogger(TaskFinderByBusinessKey.class);
    @Resource(name="processFlow")
    private ProcessFlow processFlow;



    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    private String businessKey;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String findCurrentTaskId()throws Exception {
        AppAssertUtils.notNull(this.getBusinessKey(),"please config businessKey property  for current class");
        AppAssertUtils.notNull(this.getProcessFlow(),"please config processFlow property for current class");

        String businessKeyValue = WorkFlowCommonUtils.getVarToString(this.getBusinessKey());
        AppAssertUtils.notNull(businessKeyValue,"businessKey value is null for businessKey property:"+this.getBusinessKey());

        List<TaskNode> taskNodeList =  this.getProcessFlow().findTaskByBusinessKey(businessKeyValue);
        if(AppValidationUtils.notNull(taskNodeList)){
            if(taskNodeList.size()==0){
                return taskNodeList.get(0).getTaskId();
            }else{
                logger.error("tasks  {} more than on    for current userId:{}"+taskNodeList,this.getProcessFlow().getCurrentUserId());
            }
        }else{
            logger.error("have no task for current userId:{}"+this.getProcessFlow().getCurrentUserId());

        }
        return null;


    }
}
