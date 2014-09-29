package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopTaskListener  extends ResultValue implements TaskListener{
	Logger logger = LoggerFactory.getLogger(NoResultTaskListener.class);
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		String taskId = delegateTask.getId();
		logger.info("task is create:{}",delegateTask);
		Map<String,Object> varMap = this.getResultAndLoopMap();
		logger.info("task:{},setVariables:{}",delegateTask,varMap);
		delegateTask.setVariables(varMap);
	}
}
