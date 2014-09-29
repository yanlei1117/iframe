package com.asiainfo.dbx.ln.component.workflow.activiti.taskconvert;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.util.AppBeanUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by yanlei on 2014/7/16.
 */
public class TaskNodeConverterImpl implements TaskNodeConverter{
    private final Logger logger = LoggerFactory.getLogger(TaskNodeConverterImpl.class);
    /**
     * 原生类型，需要spring look up 方式，覆盖getTaskNode方法注入
     */


    public TaskNode getTaskNode() {
        return null;
    }

    /**
     * 与ProcessFlow循环依赖，使用spring 配置时，需要指定 lazy_init=true
     */
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    @Override
    public TaskNode convert(HistoricTaskInstance task) throws Exception {
        AppAssertUtils.notNull(task,"parameter named 'task' is not allow null");
        TaskNode taskNode = this.getTaskNode();
        taskNode.setTaskId(task.getId());
        taskNode.setTaskName(task.getName());
        taskNode.setExecutionId( task.getExecutionId());
        String candidateGroupId = this.getProcessFlow().getCandidateGroupIdByHistoryTaskId(taskNode.getTaskId());
        taskNode.setCandidateGroupId(candidateGroupId);
        taskNode.setAssignee(task.getAssignee());
        AppBeanUtils.copyProperties(task,taskNode);
        Map<String,Object> variablesMap = this.getProcessFlow().getHistoryTaskVariables(task.getId());
        if(AppValidationUtils.notNull(variablesMap)){
            Object businessKey = (Object)variablesMap.get(ProcessFlow.VAR_BUSSINESS_KEY);
            if(AppValidationUtils.notNull(businessKey)){
                taskNode.setBusinessKey(businessKey.toString());
            }
            AppBeanUtils.populate(taskNode,variablesMap);
        }


        logger.info("convert(task={})={}",task,taskNode);
        return taskNode;
    }

    @Override
    public TaskNode convert(Task task)  throws Exception{
        AppAssertUtils.notNull(task,"parameter named 'task' is not allow null");
        TaskNode taskNode = this.getTaskNode();
        taskNode.setTaskId(task.getId());
        taskNode.setTaskName(task.getName());
        taskNode.setExecutionId( task.getExecutionId());
        String candidateGroupId = this.getProcessFlow().getCandidateGroupIdByHistoryTaskId(taskNode.getTaskId());
        taskNode.setCandidateGroupId(candidateGroupId);
        taskNode.setAssignee(task.getAssignee());
        AppBeanUtils.copyProperties(task,taskNode);
        Map<String,Object> variablesMap = this.getProcessFlow().getTaskVariables(task.getId());
        if(AppValidationUtils.notNull(variablesMap)){
            Object businessKey = (Object)variablesMap.get(ProcessFlow.VAR_BUSSINESS_KEY);
            if(AppValidationUtils.notNull(businessKey)){
                taskNode.setBusinessKey(businessKey.toString());
            }
            Object moduleId = (Object)variablesMap.get(ProcessFlow.VAR_MODULE_ID);
            if(AppValidationUtils.notNull(moduleId)){
                taskNode.setModuleId(moduleId.toString());
            }
            AppBeanUtils.populate(taskNode,variablesMap);
        }


        logger.info("convert(task={})={}",task,taskNode);
        return taskNode;
    }
}
