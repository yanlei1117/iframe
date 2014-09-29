package com.asiainfo.dbx.ln.business.util;

import com.asiainfo.dbx.ln.business.runner.RunnerContainer;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;

/**
 * Created by yanlei on 2014/9/23.
 */
public class AppRunnerUtils {
    private static RunnerContainer runnerContainer  = null;
    public static RunnerContainer getRunnerContainer(Class classz) throws Exception {
        if(AppRunnerUtils.runnerContainer == null){
            synchronized (AppRunnerUtils.class){
                if(AppRunnerUtils.runnerContainer == null){
                    RunnerContainer runnerContainer1 =  (RunnerContainer)AppSpringUtils.getApplicationContextHolder().getBean("runnerContainer");
                    if(runnerContainer1 == null){
                        throw new Exception(" no bean named 'runnerContainer' in spring config");
                    }else{
                        AppRunnerUtils.runnerContainer = runnerContainer1;
                    }
                }
            }
        }
        return AppRunnerUtils.runnerContainer;
    }
}
