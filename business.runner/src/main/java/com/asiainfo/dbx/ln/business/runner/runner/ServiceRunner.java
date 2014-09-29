package com.asiainfo.dbx.ln.business.runner.runner;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.exception.ExceptionFactory;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;

/**
 * Created by yanlei on 2014/9/17.
 */
public class ServiceRunner extends Runner {
    String bean;
    String operation;
    String params;
    String defineName;

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    @Override
    public void run() throws Exception {
        if(AppValidationUtils.notEmpty(this.getBean())){
             throw ExceptionFactory.createPropertyNotConfigedException("bean","ServiceRunner desc={}"+this.getDesc());
        }
        if(AppValidationUtils.notEmpty(this.getOperation())){
            throw ExceptionFactory.createPropertyNotConfigedException("operation","ServiceRunner desc={}"+this.getDesc());
        }
        AppSpringUtils.getApplicationContextHolder().getBean(this.getBean());
    }
}
