package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/20.
 */
public class CaseRunner extends Runner {
    String execute;
    String defineName;

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getExecute() {
        return execute;
    }

    public void setExecute(String execute) {
        this.execute = execute;
    }

    @Override
    public void run() throws Throwable {
        if(this.getExecute() != null){
            Object obj = AppVarUtils.getVarContainer(CaseRunner.class).getVar(this.getExecute());
            AppVarUtils.getVarContainer(CaseRunner.class).setVar(defineName,obj);
        }
        for(Runner runner:this.getRunnerList()){
            if(WhenRunner.class.isInstance(runner)){
                boolean flag = ((WhenRunner)runner).isTrue();
                if(flag){
                    runner.run();
                    break;
                }
            }
            if(ElseRunner.class.isInstance(runner)){
                runner.run();
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "\nCaseRunner{" +
                "execute='" + execute + '\'' +
                ", defineName='" + defineName + '\'' +
                ", runnerList='" + this.getRunnerList() + '\'' +
                '}';
    }
}
