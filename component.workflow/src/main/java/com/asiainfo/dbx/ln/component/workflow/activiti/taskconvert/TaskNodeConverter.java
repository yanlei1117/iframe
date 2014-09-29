package com.asiainfo.dbx.ln.component.workflow.activiti.taskconvert;

import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

/**
 * Created by yanlei on 2014/7/14.
 */
public interface TaskNodeConverter {

    public TaskNode convert(Task task) throws Exception;
    public TaskNode convert(HistoricTaskInstance task) throws Exception;
}
