package com.asiainfo.dbx.ln.component.workflow.activiti.currentuser;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取当前用户
 * Created by yanlei on 2014/7/18.
 */
public class CurrentUserFinderImpl implements  CurrentUserFinder {
    private final Logger logger = LoggerFactory.getLogger(CurrentUserFinderImpl.class);
    private String currentUserId;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public String findCurrentUserId()  {
        AppAssertUtils.notNull(this.getCurrentUserId()," property named 'currentUserId' is not allow null");
        String currentUserId =  WorkFlowCommonUtils.getVarToString(this.getCurrentUserId());
        logger.debug("findCurrentUserId:currentUserIdExpresssion:{},result={}",this.getCurrentUserId(),currentUserId);
        return currentUserId;

    }
}
