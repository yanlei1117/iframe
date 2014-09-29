package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.businesskey;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/7/16.
 */
public class TaskCreateBusinessKeyDefineImpl implements TaskCreateBusinessKeyDefine {
    private final Logger logger = LoggerFactory.getLogger(TaskCreateBusinessKeyDefineImpl.class) ;
    String businessKey;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public String getBusinessid(String taskId, String taskName, String currentUserid) {
        String businessId = null;
        if(AppValidationUtils.notNull(this.getBusinessKey())){
            businessId=   WorkFlowCommonUtils.getVarToString(this.getBusinessKey());
        }
        return businessId;
    }
}
