package com.asiainfo.dbx.ln.component.workflow.activiti.aop;

/**
 * Created by yanlei on 2014/7/24.
 */
public class ServiceMethodThreadLocal {
    private static ThreadLocal<String> methodThreadLocal= new ThreadLocal<String>();
    public static void setMethodLongName(String methodLongName){
        methodThreadLocal.set(methodLongName);
    }
    public static String getMethodLongName(){
        return methodThreadLocal.get();
    }

}
