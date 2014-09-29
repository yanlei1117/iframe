package com.asiainfo.dbx.ln.component.workflow.activiti.start;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by yanlei on 2014/7/17.
 */
public class ProcessFlowStartListenerImpl implements  ProcessFlowStartListener {
    private final Logger logger = LoggerFactory.getLogger(ProcessFlowStartListenerImpl.class);
    String processKey;


    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    public void startProcessFlow(){
        try {
            if (AppValidationUtils.notNull(this.getProcessKey())) {
                String processKeyValue = WorkFlowCommonUtils.getVarToString(this.getProcessKey());
                if (AppValidationUtils.notNull(processKeyValue)) {
                    if (AppValidationUtils.notNull(this.getProcessFlow())) {
                        logger.info("WORKFLOW START ! process id:{} ",processKeyValue);
                        this.getProcessFlow().startFlowByProcessKey(processKeyValue);

                    } else {
                        logger.error("startProcessFlow fail: property named:'processFlow' is null");
                    }
                } else {
                    logger.error("startProcessFlow fail: find processKey values is null, exepression property named:'processKey' is {}", this.getProcessKey());
                }
            } else {
                logger.error("startProcessFlow fail: property named:'processKey' is null");
            }
        }catch(Exception e){
            logger.error("startProcessFlow fail,property named:'processKey' is '"+this.getProcessKey()+"'",e);
        }
    }


}
