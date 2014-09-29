package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.candidategroup;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/7/16.
 */
public class TaskCreateCandidateGroupDefineImpl implements TaskCreateCandidateGroupDefine {
    private final Logger logger = LoggerFactory.getLogger(TaskCreateCandidateGroupDefineImpl.class);
   private String candidateGroupId;

    public String getCandidateGroupId() {
        return candidateGroupId;
    }

    public void setCandidateGroupId(String candidateGroupId) {
        this.candidateGroupId = candidateGroupId;
    }

    @Override
    public String getCandidateGroup(String newTaskId, String newTaskName, String businessId, String currentUserId) {
        String candidateGroupIdVar = null;
        if(AppValidationUtils.notNull(this.getCandidateGroupId()) ){
                candidateGroupIdVar = WorkFlowCommonUtils.getVarToString(this.getCandidateGroupId());
        }
        return candidateGroupIdVar;
    }
}
