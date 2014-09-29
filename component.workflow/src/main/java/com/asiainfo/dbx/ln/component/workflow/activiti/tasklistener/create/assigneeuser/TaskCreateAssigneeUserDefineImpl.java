package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.assigneeuser;

import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.assigneeuser.TaskCreateAssigneeUserDefine;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;

import javax.annotation.Resource;

/**
 * Created by yanlei on 2014/7/15.
 */
public class TaskCreateAssigneeUserDefineImpl implements TaskCreateAssigneeUserDefine {
    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }
    private String  assigneeUserId;
    public String getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(String assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    @Override
    public String getAssigneeUser(String taskId, String businessId, String currentUserId) {
       String  assigneeUserId = WorkFlowCommonUtils.getAssigneeUserId(this.getProcessFlow(), this.getAssigneeUserId());
        return assigneeUserId;
    }
}
