package com.asiainfo.dbx.ln.component.workflow.activiti.flow;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.Event.FlowEvent;
import com.asiainfo.dbx.ln.component.workflow.activiti.flow.task.UserTaskDefine;
import com.asiainfo.dbx.ln.component.workflow.activiti.start.ProcessFlowStartListener;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yanlei on 2014/7/24.
 */
public class ProcessDefine {
    private final Logger logger = LoggerFactory.getLogger(ProcessDefine.class);
    String processName;
    private  String startFlowEventSource = null;
    boolean fireStartFlow = false;

    public boolean isFireStartFlow() {
        return fireStartFlow;
    }

    public void setFireStartFlow(boolean fireStartFlow) {
        this.fireStartFlow = fireStartFlow;
    }

    public String getStartFlowEventSource() {
        return startFlowEventSource;
    }

    public void setStartFlowEventSource(String startFlowEventSource) {
        this.startFlowEventSource = startFlowEventSource;
    }

    private ProcessFlowStartListener startFlow;

    public ProcessFlowStartListener getStartFlow() {
        return startFlow;
    }

    public void setStartFlow(ProcessFlowStartListener startFlow) {
        this.startFlow = startFlow;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }


    public Set<String> getEventSourceSet(){
        Set<String>  eventSourceSet = new HashSet<String> ();
        if(AppValidationUtils.notNull(this.getStartFlowEventSource())) {
            eventSourceSet.add(this.getStartFlowEventSource());
        }
        if(AppValidationUtils.notNull(this.getUserTaskDefineList())){
            for(UserTaskDefine userTaskDefine:this.getUserTaskDefineList()){
                userTaskDefine.fillEventSource(eventSourceSet);
            }
        }
        return eventSourceSet;
    }

    public boolean fireEvent(FlowEvent flowEvent){
        if(!this.isFireStartFlow()){
            this.setFireStartFlow(true);
            this.getStartFlow().startProcessFlow();
            return true;
        }else if(AppValidationUtils.notNull(this.getUserTaskDefineList())){
            for(UserTaskDefine userTaskDefine:this.getUserTaskDefineList()){
                boolean result = userTaskDefine.fireEvent(flowEvent);
                if(result){
                   return true;
                }
            }
        }
        return false;
    }


    List<UserTaskDefine> userTaskDefineList;

    public List<UserTaskDefine> getUserTaskDefineList() {
        return userTaskDefineList;
    }

    public void setUserTaskDefineList(List<UserTaskDefine> userTaskDefineList) {
        this.userTaskDefineList = userTaskDefineList;
    }




}
