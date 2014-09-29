package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;


import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.aop.ServiceMethodThreadLocal;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.FlowEvent;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.ProcessDefine;

import com.asiainfo.dbx.ln.component.workflow.activiti.model.TaskThreadLocal;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 由Activit 配置文件的taskListnere ->create->调用，实现任务创建时参数的设置
 * Created by yanlei on 2014/7/24.
 */
public class UserTaskFireCreateImpl implements  UserTaskFireCreate{
    private final Logger logger = LoggerFactory.getLogger(UserTaskFireCreateImpl.class);
    private ProcessDefine processDefine;

    public ProcessDefine getProcessDefine() {
        return processDefine;
    }

    public void setProcessDefine(ProcessDefine processDefine) {
        this.processDefine = processDefine;
    }

    @Resource(name="currentTask")
    private TaskThreadLocal taskThreadLocal = null;

    public TaskThreadLocal getTaskThreadLocal() {
        return taskThreadLocal;
    }

    public void setTaskThreadLocal(TaskThreadLocal taskThreadLocal) {
        this.taskThreadLocal = taskThreadLocal;
    }

    @Override
    public void createTask(DelegateTask delegateTask) {
        //设置当前任务ID，到ThreadLoacl中，以便从变量池中获取到任务ID相关的其它信息

        FlowEvent flowEvent =   FlowEvent.createTaskEvent(delegateTask, ServiceMethodThreadLocal.getMethodLongName());
        boolean createTaskFlag = this.processDefine.fireEvent(flowEvent);
        if(!createTaskFlag){
            logger.error("not found  UserTaskCompleteDefine matched to create task,flowEvent:{}",flowEvent);
            throw new RuntimeException("not found  UserTaskCompleteDefine matched to create task,flowEvent:{}"+flowEvent);
        }else{
            logger.info("Task create Finish:{}",delegateTask);
        }
        if(AppValidationUtils.notNull(this.getTaskThreadLocal())){
            this.getTaskThreadLocal().setCurrentTaskId(delegateTask.getId());
        }
    }
}
