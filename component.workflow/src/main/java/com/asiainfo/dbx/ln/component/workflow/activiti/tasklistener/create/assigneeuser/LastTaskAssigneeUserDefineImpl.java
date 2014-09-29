package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.assigneeuser;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取上一任务的受理人
 * Created by yanlei on 2014/7/18.
 */
public class LastTaskAssigneeUserDefineImpl implements LastTaskAssigneeUserDefine{
    private final Logger logger = LoggerFactory.getLogger(LastTaskAssigneeUserDefineImpl.class);
    private String lastTaskAssigneeUserId;

    public String getLastTaskAssigneeUserId() {
        return lastTaskAssigneeUserId;
    }

    public void setLastTaskAssigneeUserId(String lastTaskAssigneeUserId) {
        this.lastTaskAssigneeUserId = lastTaskAssigneeUserId;
    }

    @Override
    public String findLastTaskAssigneeUserId() {
        AppAssertUtils.notNull(this.getLastTaskAssigneeUserId(), " property named 'lastTaskAssigneeUserId' is not allow null");
        String lastTaskAssigneeUserId =  WorkFlowCommonUtils.getVarToString(this.getLastTaskAssigneeUserId());
        logger.debug("findLastTaskAssigneeUserId:lastTaskAssigneeUserIdExpreesion:{},result={}",this.getLastTaskAssigneeUserId(),lastTaskAssigneeUserId);
        return lastTaskAssigneeUserId;
    }
}
