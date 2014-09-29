package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/9/20.
 */
public class LogicFalseRunner extends  Runner{
    private static final Logger logger = LoggerFactory.getLogger(LogicTrueRunner.class);


    String expression;
    String callProcedureName;
    String callProcedureParams;
    String callProcedureResultName;

    public String getCallProcedureName() {
        return callProcedureName;
    }

    public void setCallProcedureName(String callProcedureName) {
        this.callProcedureName = callProcedureName;
    }

    public String getCallProcedureResultName() {
        return callProcedureResultName;
    }

    public void setCallProcedureResultName(String callProcedureResultName) {
        this.callProcedureResultName = callProcedureResultName;
    }

    public String getCallProcedureParams() {
        return callProcedureParams;
    }

    public void setCallProcedureParams(String callProcedureParams) {
        this.callProcedureParams = callProcedureParams;
    }

    public String getExpression() {
        return expression;
    }



    public void setExpression(String expression)  throws Exception{
        this.expression = expression;
    }
    @Override
    public void run() throws Throwable {
        logger.debug("LogicTrueRunner expression:{}  start...",this.expression);
        Boolean result = (Boolean) AppVarUtils.getVarContainer(LogicTrueRunner.class).getVar(this.expression);
        logger.debug("LogicTrueRunner expression:{}  result:{} finish",this.expression,result);
        if(!result){
            for(Runner runner:this.getRunnerList()){
                runner.run();
            }
        }
    }

    @Override
    public String toString() {
        return "\nLogicFalseRunner{" +
                "expression='" + expression + '\'' +
                ", callProcedureName='" + callProcedureName + '\'' +
                ", callProcedureParams='" + callProcedureParams + '\'' +
                ", callProcedureResultName='" + callProcedureResultName + '\'' +
                ", runnerList='" + this.getRunnerList() + '\'' +
                '}';
    }
}
