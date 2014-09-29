package com.asiainfo.dbx.ln.component.workflow.activiti.ExecutionListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/7/10.
 */
public class ExecutionCreateListner implements ExecutionListener{
    Logger logger = LoggerFactory.getLogger(ExecutionCreateListner.class);
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        logger.info("new execution create:{}",execution);
    }
}
