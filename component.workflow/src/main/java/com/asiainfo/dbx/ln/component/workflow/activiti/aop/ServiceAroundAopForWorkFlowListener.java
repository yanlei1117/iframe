package com.asiainfo.dbx.ln.component.workflow.activiti.aop;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.start.ProcessFlowStartListener;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.TaskCompleteListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 对service 方法加上代理，使service方法的入参与出参存入变量池。以使工作流能够获取到相应的数据，如业务ID,TASKID，等等。
 *
 * 如果配置了启动工作流的监听器，则调用该监听器启动流程（当前Service 方法完成意味着启动某个流程）
 * 如果配置了启动任务完成监听器，则调用该监听器来完成任务 （当前Service 方法完成意味着某个任务已完成）
 *
 * Created by yanlei on 2014/7/17.
 */
public class ServiceAroundAopForWorkFlowListener {
    private final Logger logger = LoggerFactory.getLogger(ServiceAroundAopForWorkFlowListener.class);

        private ProcessFlowStartListener processFlowStartListener;

    public ProcessFlowStartListener getProcessFlowStartListener() {
        return processFlowStartListener;
    }

    public void setProcessFlowStartListener(ProcessFlowStartListener processFlowStartListener) {
        this.processFlowStartListener = processFlowStartListener;
    }
    private TaskCompleteListener taskCompleteListener;

    public TaskCompleteListener getTaskCompleteListener() {
        return taskCompleteListener;
    }

    public void setTaskCompleteListener(TaskCompleteListener taskCompleteListener) {
        this.taskCompleteListener = taskCompleteListener;
    }

    public Object onServiceMethodCalled(ProceedingJoinPoint pjp){
           Object result = null;
           try {
               Object objs [] = pjp.getArgs();
               Signature signature = pjp.getSignature();
               result = pjp.proceed();
               Map<String,Object> map = new HashMap<String,Object>(2);
               map.put("PARAMETERS",objs);
               map.put("RESULT",result);
               AppVarUtils.getVarContainer(ServiceAroundAopForWorkFlowListener.class).setVar("SERVICE_METHOD",map);
               logger.info("{}:PARAMETERS:{},RESULT:{}",signature,objs,result);
               if(AppValidationUtils.notNull(this.getProcessFlowStartListener())){
                   this.getProcessFlowStartListener().startProcessFlow();
               }
               if(AppValidationUtils.notNull(this.getTaskCompleteListener())){
                   this.getTaskCompleteListener().completeTask();
               }
           }catch (Throwable e){
               logger.error("",e);
           }
           return result;
       }
}
