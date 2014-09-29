package com.asiainfo.dbx.ln.business.runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/9/15.
 */
public abstract class Runner {
    public abstract void run() throws Throwable;
    protected List<Runner> runnerList = new ArrayList<Runner>();
    public  void addRunner(Runner runner){
        this.runnerList.add(runner);
    }
    public  void addRunner(List<Runner> runnerList){
        this.runnerList.addAll(runnerList);
    }
    public List<Runner> getRunnerList() {
        return runnerList;
    }
    String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
