package com.asiainfo.dbx.ln.component.workflow.activiti.aop;

import com.asiainfo.dbx.ln.component.workflow.activiti.flow.ProcessDefine;
import org.aopalliance.aop.Advice;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;

import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 * 对ProcessDefine中定义的事件源(service)进行代理，以实现在service调用时触发任务完成
 */
public class ServiceMethodPointcutAdvisor extends RegexpMethodPointcutAdvisor {
    private ProcessDefine processDefine;

    public ProcessDefine getProcessDefine() {
        return processDefine;
    }

    public void setProcessDefine(ProcessDefine processDefine) {
        this.processDefine = processDefine;
       Set<String> eventSourceSet =  this.processDefine.getEventSourceSet();
        String patterns [] = new String[eventSourceSet.size()];
        eventSourceSet.toArray(patterns);
        this.setPatterns(patterns);
    }


    @Override
    public Advice getAdvice() {
        ServcieMethodInterceptor taskInterceptor = new ServcieMethodInterceptor();
        taskInterceptor.setProcessDefine(this.getProcessDefine());
        return taskInterceptor;
    }
}
