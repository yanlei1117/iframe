package com.asiainfo.dbx.ln.component.workflow.activiti.aop;

import com.asiainfo.dbx.ln.component.util.AppBeanUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.FlowEvent;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.ProcessDefine;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanlei on 2014/7/23.
 */
public class ServcieMethodInterceptor implements MethodInterceptor{
    private Logger logger = LoggerFactory.getLogger(ServcieMethodInterceptor.class);

    private ProcessDefine processDefine;

    public ProcessDefine getProcessDefine() {
        return processDefine;
    }

    public void setProcessDefine(ProcessDefine processDefine) {
        this.processDefine = processDefine;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object targetObj = methodInvocation.getThis();
        String className = targetObj.getClass().getName();
        String classSimpleName = targetObj.getClass().getSimpleName();
        Method method = methodInvocation.getMethod();
        String methodLongName = className+"."+method.getName();
        logger.info("method named:{} is intercepted",methodLongName);

        Object [] params = methodInvocation.getArguments();
        Object result = methodInvocation.proceed();
        String methodParameterNames [] = AppBeanUtils.getMethodParameterNames(method);
        Map<String,Object> map = new HashMap<String,Object>(2);

        for(int i=0;i<params.length;i++){
            map.put(methodParameterNames[i],params[i]);
        }
        map.put("return",result);
        Map<String,Map<String,Object>> methodMap = new  HashMap<String,Map<String,Object>>(1);
        methodMap.put(method.getName(),map);
        AppVarUtils.getVarContainer(ServcieMethodInterceptor.class).setVar(classSimpleName, methodMap);
        logger.info("{}: set to VarContainer:{}->{}",method,classSimpleName,methodMap);

        Map<String,Object> newMap = new HashMap<String,Object>(2);
        newMap.put("PARAMETERS", Arrays.asList(params));
        newMap.put("RESULT",result);
        AppVarUtils.getVarContainer(ServcieMethodInterceptor.class).setVar("SERVICE_METHOD", newMap);
        logger.info("{}: set to VarContainer:SERVICE_METHOD->{}", method, newMap);
        ServiceMethodThreadLocal.setMethodLongName(methodLongName);
        FlowEvent flowEvent = FlowEvent.completeTaskEvent(methodLongName);
        boolean fireEventResult = this.getProcessDefine().fireEvent(flowEvent);//触发任务完成。
        if(!fireEventResult){
            logger.error(" not found task for {}",method);
        }
        return result;
    }
}
