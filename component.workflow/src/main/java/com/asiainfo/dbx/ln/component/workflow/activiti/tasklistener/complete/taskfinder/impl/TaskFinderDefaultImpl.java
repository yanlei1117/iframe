package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.impl;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.TaskFinder;

import java.util.List;

/**
 * 多种方式组合查找当前任务
 * Created by yanlei on 2014/7/16.
 */
public class TaskFinderDefaultImpl implements TaskFinder {

    private List<TaskFinder> taskFinderListList;

    public List<TaskFinder> getTaskFinderListList() {
        return taskFinderListList;
    }

    public void setTaskFinderListList(List<TaskFinder> taskFinderListList) {
        this.taskFinderListList = taskFinderListList;
    }

    @Override
    public String findCurrentTaskId() throws Exception{
        String taskId = null;
        for(TaskFinder taskFinder:taskFinderListList){
             taskId = taskFinder.findCurrentTaskId();
            if(AppValidationUtils.notNull(taskId)){
                break;
            }
        }
        return taskId;
    }
}
