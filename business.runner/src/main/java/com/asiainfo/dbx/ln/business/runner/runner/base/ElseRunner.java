package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class ElseRunner extends Runner {

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
        final StringBuilder sb = new StringBuilder("\nElseRunner{");
        sb.append("expression='").append(expression).append('\'');
        sb.append(", callProcedureName='").append(callProcedureName).append('\'');
        sb.append(", callProcedureParams='").append(callProcedureParams).append('\'');
        sb.append(", callProcedureResultName='").append(callProcedureResultName).append('\'');
        sb.append(", runnerList=").append(this.getRunnerList());

        sb.append('}');
        return sb.toString();
    }
}
