package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.TaskCompleteListener;

import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 */
public class UserTaskCompleteDefineImpl  implements  UserTaskCompleteDefine{
    private String completeEventSource;

    public String getCompleteEventSource() {
        return completeEventSource;
    }

    public void setCompleteEventSource(String completeEventSource) {
        this.completeEventSource = completeEventSource;
    }

    private TaskCompleteListener complete;
    public TaskCompleteListener getComplete() {
        return complete;
    }

    public void setComplete(TaskCompleteListener complete) {
        this.complete = complete;
    }
    public void completeTask() {
        this.getComplete().completeTask();
    }
    public boolean matchEventSource(String eventSource){
        return AppStringUtils.matchPartString(this.getCompleteEventSource(), eventSource);
    }

    @Override
    public void fillEventSource(Set<String>  eventSourceSet) {
        if(AppValidationUtils.notNull(this.getCompleteEventSource())){
            eventSourceSet.add(this.getCompleteEventSource());
        }
    }
}
