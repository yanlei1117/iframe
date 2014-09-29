package com.asiainfo.dbx.ln.component.workflow.activiti.model;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/7/29.
 */
public class CurrentTask extends TaskNode implements  TaskThreadLocal {
    private final Logger logger = LoggerFactory.getLogger(CurrentTask.class);
    private  ThreadLocal<String> taskIdThreadLocal= new ThreadLocal<String>();
    private  ThreadLocal<TaskNode> taskNodeThreadLocal= new ThreadLocal<TaskNode>();

    @Override
    public void setCurrentTaskId(String taskId) {
        taskIdThreadLocal.set(taskId);
        logger.info("setCurrentTaskId(taskId:{})",taskId);
    }
    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }


    public  List<TaskNode> getCompleteTaskListInExecution()  {
        try {
            return this.getProcessFlow().findCompleateTaskInExecution(this.taskIdThreadLocal.get());
        }catch(Exception e){
            logger.error("",e);
        }
        return null;
    }
    private void init()  {
        try {
            if (AppValidationUtils.isNull(taskNodeThreadLocal.get()) || !taskNodeThreadLocal.get().getTaskId().equals(taskIdThreadLocal.get())) {
                TaskNode taskNode = this.getProcessFlow().findTaskByTaskId(taskIdThreadLocal.get());
                taskNodeThreadLocal.set(taskNode);
            }
        }catch(Exception e){
            logger.error("",e);
        }
    }


    @Override
    public String getExecutionId() {
        init();
        return taskNodeThreadLocal.get().getExecutionId();
    }

    @Override
    public String getAssignee() {
        init();
        return taskNodeThreadLocal.get().getAssignee();
    }

    @Override
    public String getCandidateGroupId() {
        init();
        return  taskNodeThreadLocal.get().getCandidateGroupId();
    }

    @Override
    public String getTaskName() {
        init();
        return taskNodeThreadLocal.get().getTaskName();
    }

    @Override
    public Map<String, Object> getVariablesMap() {
        init();
        return taskNodeThreadLocal.get().getVariablesMap();
    }

    @Override
    public String getBusinessKey() {
        init();
        return taskNodeThreadLocal.get().getBusinessKey();
    }

    @Override
    public String getModuleId() {
        init();
        return taskNodeThreadLocal.get().getModuleId();
    }

    @Override
    public String getTaskId() {
        init();
        return taskNodeThreadLocal.get().getTaskId();
    }

  /*  @Override
    public String getVarName() {
        return "CURRENT_TASK";
    }

    @Override
    public Object getVarValue() throws Exception {
        return this;
    }*/
}
