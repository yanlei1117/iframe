package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.CreateTaskEvent;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.FlowEvent;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.TaskCompleteListener;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.create.TaskCreateListener;
import org.activiti.engine.delegate.DelegateTask;

import java.util.List;
import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 */
public class UserTaskDefineImpl implements UserTaskDefine {
    String taskName;

    public String getTaskName() {
        return this.taskName;
    }


    public void setTaskName(String taskName) {
       this.taskName = taskName;
    }

    List<UserTaskCreateDefine> createDefines;

    List<UserTaskCompleteDefine> completeDefines;

    public List<UserTaskCreateDefine> getCreateDefines() {
        return createDefines;
    }

    public void setCreateDefines(List<UserTaskCreateDefine> createDefines) {
        this.createDefines = createDefines;
    }

    public List<UserTaskCompleteDefine> getCompleteDefines() {
        return completeDefines;
    }

    public void setCompleteDefines(List<UserTaskCompleteDefine> completeDefines) {
        this.completeDefines = completeDefines;
    }

    @Override
    public void fillEventSource(Set<String>  eventSourceSet) {
        if(AppValidationUtils.notNull(this.getCompleteDefines())){
            for(UserTaskCompleteDefine userTaskCompleteDefine:this.getCompleteDefines()){
                userTaskCompleteDefine.fillEventSource(eventSourceSet);
            }
        }
    }

    @Override
    public boolean fireEvent(FlowEvent flowEvent) {
        if(flowEvent.getEventType().equalsIgnoreCase(FlowEvent.CREATE_EVENT)){
            CreateTaskEvent createTaskEvent = (CreateTaskEvent)flowEvent;
            if(createTaskEvent.getTaskName().equalsIgnoreCase(this.getTaskName())){
                if(AppValidationUtils.notNull(this.getCreateDefines())){
                    for(UserTaskCreateDefine userTaskCreateDefine:this.getCreateDefines()){
                         if(userTaskCreateDefine.matchEventSource(flowEvent.getEventSource())){
                             userTaskCreateDefine.createTask(((DelegateTask) createTaskEvent.getSourceObject()));
                             return true;
                         }
                    }
                }
            }
        }else if(flowEvent.getEventType().equalsIgnoreCase(FlowEvent.COMPLEATE_EVENT)){
            String eventSource = flowEvent.getEventSource();
            if(AppValidationUtils.notNull(this.getCompleteDefines())){
                for(UserTaskCompleteDefine userTaskCompleteDefine:this.getCompleteDefines()){
                    if(userTaskCompleteDefine.matchEventSource(eventSource)){
                        userTaskCompleteDefine.completeTask();
                        return true;
                    }
                }
            }
        }
        return false;
    }



}
