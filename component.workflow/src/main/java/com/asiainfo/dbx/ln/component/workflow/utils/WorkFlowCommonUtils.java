package com.asiainfo.dbx.ln.component.workflow.utils;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yanlei on 2014/7/15.
 */

@SuppressWarnings("unchecked")
public class WorkFlowCommonUtils {
        private static final Logger logger = LoggerFactory.getLogger(WorkFlowCommonUtils.class);
        public static String getAssigneeUserId(ProcessFlow processFlow,String assigneeUserIdVar){
            String assigneeUserId = null;
            if(AppValidationUtils.notNull(assigneeUserIdVar)) {
                if (assigneeUserIdVar.equalsIgnoreCase(ProcessFlow.VAR_CURRENT_USER_ID)) {
                    assigneeUserId = processFlow.getCurrentUserId();
                } else {
                    try {
                        Object assigneeUser = AppVarUtils.getVarContainer(WorkFlowCommonUtils.class).getVar(assigneeUserIdVar);
                        if(AppValidationUtils.notNull(assigneeUser)){
                            assigneeUserId = assigneeUser.toString();
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
            return assigneeUserId;
        }

    public   static Map<String,Object> convertVarMap(Map<String,Object> varMap ){
        Map<String,Object> realVarMap = new HashMap(varMap.size());
        Iterator<String> iterator = varMap.keySet().iterator();
        while(iterator.hasNext()){
            String propertyName  = iterator.next();
            try{
                Object value =varMap.get(propertyName);
                propertyName = WorkFlowCommonUtils.getVarToString(propertyName);

                if(value instanceof  String){
                    value = WorkFlowCommonUtils.getVar((String)value);
                }
                realVarMap.put(propertyName,value);
            }catch(Exception e){
                logger.error("",e);
            }
        }
        return realVarMap;
    }

     public static Object getVar(String varName){
         Object value = null;
         try{
             value = (AppVarUtils.getVarContainer(WorkFlowCommonUtils.class).getVar(varName).toString());
         }catch(Exception e){
             logger.error("getVar(varName:"+varName+")",e);
         }
         return value;
     }
    public static String getVarToString(String varName){
        Object value = getVar(varName);
        if(AppValidationUtils.notNull(value)){
            return value.toString();
        }else{
            return null;
        }

    }

    public static void setVar(String name,Object obj) throws Exception{
        AppVarUtils.getVarContainer(WorkFlowCommonUtils.class).setVar(name,obj);
    }
    public static void setCurrentUserId(String currentUserId) throws Exception{
        WorkFlowCommonUtils.setVar(ProcessFlow.VAR_CURRENT_USER_ID,currentUserId);
    }
    public static void setTaskId(String taskId) throws Exception{
        WorkFlowCommonUtils.setVar(ProcessFlow.VAR_TASK_ID,taskId);
    }
}
