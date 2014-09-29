package com.asiainfo.dbx.ln.component.workflow.activiti.flow.task;

import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.FlowEvent;
import org.activiti.engine.delegate.DelegateTask;

import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 */
public interface UserTaskDefine {

    public boolean fireEvent(FlowEvent flowEvent);
    public void fillEventSource(Set<String> eventSourceSet);
}
