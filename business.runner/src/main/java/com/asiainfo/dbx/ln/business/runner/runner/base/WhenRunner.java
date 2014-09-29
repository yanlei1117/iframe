package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/9/20.
 */
public class WhenRunner extends Runner {
    private Logger logger = LoggerFactory.getLogger(WhenRunner.class);
    String expression;
    String callProcedureName;
    String callProcedureParams;
    String callProcedureResultName;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getCallProcedureName() {
        return callProcedureName;
    }

    public void setCallProcedureName(String callProcedureName) {
        this.callProcedureName = callProcedureName;
    }

    public String getCallProcedureParams() {
        return callProcedureParams;
    }

    public void setCallProcedureParams(String callProcedureParams) {
        this.callProcedureParams = callProcedureParams;
    }

    public String getCallProcedureResultName() {
        return callProcedureResultName;
    }

    public void setCallProcedureResultName(String callProcedureResultName) {
        this.callProcedureResultName = callProcedureResultName;
    }


    public boolean isTrue() throws Exception  {
        logger.debug("WhenRunner expression:{}  start...",this.expression);
        Boolean result = (Boolean) AppVarUtils.getVarContainer(LogicTrueRunner.class).getVar(this.expression);
        logger.debug("WhenRunner expression:{}  result:{} finish",this.expression,result);
        return result;
    }

    @Override
    public void run() throws Throwable {
            if(AppValidationUtils.notEmpty(this.getCallProcedureName())){
                CallProcedureRunner callProcedureRunner =  new CallProcedureRunner();
                callProcedureRunner.setParamsVarName(this.getCallProcedureParams());
                callProcedureRunner.setDefineName(this.getCallProcedureResultName());
                callProcedureRunner.run();
            }

            for(Runner runner:this.getRunnerList()){
                runner.run();
            }

    }

    @Override
    public String toString() {
        return "\nWhenRunner{" +
                "expression='" + expression + '\'' +
                ", callProcedureName='" + callProcedureName + '\'' +
                ", callProcedureParams='" + callProcedureParams + '\'' +
                ", callProcedureResultName='" + callProcedureResultName + '\'' +
                ", runnerList='" + this.getRunnerList() + '\'' +
                '}';
    }
}
