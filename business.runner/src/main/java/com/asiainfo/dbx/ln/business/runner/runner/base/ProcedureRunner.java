package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/19.
 */
public class ProcedureRunner extends Runner {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String returnVarName;
    String returnVarInitVar;
    String paramsMapVarName;
    String paramsMapKeys;
    String repository;
    String collection;

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getReturnVarName() {
        return returnVarName;
    }

    public void setReturnVarName(String returnVarName) {
        this.returnVarName = returnVarName;
    }

    public String getReturnVarInitVar() {
        return returnVarInitVar;
    }

    public void setReturnVarInitVar(String returnVarInitVar) {
        this.returnVarInitVar = returnVarInitVar;
    }

    public String getParamsMapVarName() {
        return paramsMapVarName;
    }

    public void setParamsMapVarName(String paramsMapVarName) {
        this.paramsMapVarName = paramsMapVarName;
    }

    public String getParamsMapKeys() {
        return paramsMapKeys;
    }

    public void setParamsMapKeys(String paramsMapKeys) {
        this.paramsMapKeys = paramsMapKeys;
    }

    @Override
    public void run() throws Throwable {
        if(this.returnVarInitVar != null){
            Object returnVarInitVarValue = AppVarUtils.getVarContainer(ProcedureRunner.class).getVar(this.returnVarInitVar);
            AppVarUtils.getVarContainer(ProcedureRunner.class).setVar(this.getReturnVarName(),returnVarInitVarValue);
        }
        for(Runner runner:this.getRunnerList()){
            runner.run();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nProcedureRunner{");
        sb.append("name='").append(name).append('\'');
        sb.append("runnerList=").append(this.getRunnerList());

        sb.append('}');
        return sb.toString();
    }
}
