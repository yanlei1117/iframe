package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.parameter;

import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 任务参数定义
 * Created by yanlei on 2014/7/15.
 */
public class TaskCreateParameterDefineImpl implements TaskCreateParameterDefine {

    private final Logger logger = LoggerFactory.getLogger(TaskCreateParameterDefineImpl.class);
    Map<String,Object> varMap ;
    public Map<String, Object> getVarMap() {
        return varMap;
    }

    public void setVarMap(Map<String, Object> varMap) {
        this.varMap = varMap;
    }


    @Override
    public Map<String, Object> getParameterMap(String newTaskId,String newTaskName, String businessId, String currentUserId) {
       Map<String,Object> realVarMap = WorkFlowCommonUtils.convertVarMap(this.getVarMap());
        logger.debug("getParameterMap(newTaskId:{},newTaskName:{},businessId:{},currentUserId:{}),varMap:{},result={}",newTaskId,newTaskName,businessId,currentUserId,varMap,realVarMap);
        return realVarMap;
    }
}
