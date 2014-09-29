package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskResult.impl;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskResult.TaskResultFinder;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * 获取任务的执行结果：由原变量名找对应的值。并以目标变量名为key,重新生成结果Map
 *
 * Created by yanlei on 2014/7/16.
 */
public class TaskResultFinderImpl implements TaskResultFinder{
    /**
     * key :为变量名 （原变量名）
     * value:为需要设置在变量流程中的变量名（目标变量名）
     */
    private Map<String,String> resultVarMap ;

    public Map<String, String> getResultVarMap() {
        return resultVarMap;
    }

    public void setResultVarMap(Map<String, String> resultVarMap) {
        this.resultVarMap = resultVarMap;
    }

    @Override
    public Map<String, Object> findTaskResult() {
        AppAssertUtils.notNull(this.getResultVarMap()," please config resultVarMap property for this class");
        Iterator<String> varNamesIterator = this.getResultVarMap().keySet().iterator();
        Map<String,Object> resultMap = new HashMap<String, Object>(this.getResultVarMap().size());
        while(varNamesIterator.hasNext()){
            String varName = varNamesIterator.next();
            String newVarName = this.getResultVarMap().get(varName);
            Object varValue = WorkFlowCommonUtils.getVar(varName);
            resultMap.put(newVarName,varValue);
        }
        return resultMap;
    }
}
