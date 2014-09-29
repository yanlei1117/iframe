package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultTaskListener extends ResultValue implements TaskListener{
	Logger logger = LoggerFactory.getLogger(ResultTaskListener.class);
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		String taskId = delegateTask.getId();
		logger.info("task is create:{}",delegateTask);
		Map<String,Object> varMap = this.getResultMap();
		logger.info("task:{},setVariables:{}",delegateTask,varMap);
		delegateTask.setVariables(varMap);
	}
}
