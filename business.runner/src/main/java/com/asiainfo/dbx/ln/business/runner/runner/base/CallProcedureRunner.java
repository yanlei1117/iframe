package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.util.AppRunnerUtils;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/9/20.
 */
public class CallProcedureRunner extends Runner {
    private final static Logger logger = LoggerFactory.getLogger(CallProcedureRunner.class);
    String repository;
    String container;
    String procedureName;
    String paramsVarName;
    String defineName;

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getParamsVarName() {
        return paramsVarName;
    }

    public void setParamsVarName(String paramsVarName) {
        this.paramsVarName = paramsVarName;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    @Override
    public void run() throws Throwable {
        boolean flag = false;
        try {
            ProcedureResourceDefine procedureResourceDefine = ResourceDefineFactory.getProcedureResourceDefine(this.getRepository(), this.getContainer(), this.getProcedureName());
            AppRunnerUtils.getRunnerContainer(CallProcedureRunner.class).runProcedure(procedureResourceDefine, this.getParamsVarName(), this.getDefineName());
            flag = true;
        }finally {
            if(flag){
                logger.info("run finish,result is 'OK',{} ",this.toString());
            }else{
                logger.error("run error:{} ",this.toString());
            }

        }
    }

    @Override
    public String toString() {
        return "\nCallProcedureRunner{" +
                "procedureName='" + procedureName + '\'' +
                ", paramsVarName='" + paramsVarName + '\'' +
                ", defineName='" + defineName + '\'' +
                ", runnerList='" + this.getRunnerList() + '\'' +
                '}';
    }
}
