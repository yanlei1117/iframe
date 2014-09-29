package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.exception.ftp.FtpServerPathNotFoundException;
import com.asiainfo.dbx.ln.component.net.ftp.FtpOperator;

import com.asiainfo.dbx.ln.component.net.ftp.FtpOperatorService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/9/22.
 */
public class AppFtpUtils {
    private   static Map<String,FtpOperator> ftpOperatorMap = new ConcurrentHashMap<String,FtpOperator>();

    public static FtpOperator getFtpOperator(Class classz,String ftpOperatorName) throws Exception{
        if(AppFtpUtils.ftpOperatorMap.size()==0){
            synchronized (AppJsonUtils.class){
                if(AppFtpUtils.ftpOperatorMap.size()==0) {
                    Map<String,FtpOperator> ftpOperatorMap1=  AppSpringUtils.getApplicationContextHolder().getBeansOfType(FtpOperator.class);
                    if (ftpOperatorMap1 == null) {
                        throw new Exception("no bean typed 'FtpOperator.class'");
                    }
                    AppFtpUtils.ftpOperatorMap.putAll(ftpOperatorMap1);
                }
            }
        }
        FtpOperator ftpOperator =  AppFtpUtils.ftpOperatorMap.get(ftpOperatorName);
        return ftpOperator;
    }
    private static  FtpOperatorService ftpOperatorService = null;

    public static FtpOperatorService getFtpOperatorService(Class classz) throws Exception{
        if(AppFtpUtils.ftpOperatorService == null){
            synchronized (AppFtpUtils.class){
                if(AppFtpUtils.ftpOperatorService ==null) {
                    FtpOperatorService ftpOperatorService1 = (FtpOperatorService) AppSpringUtils.getApplicationContextHolder().getBean("ftpOperatorService");
                    if (ftpOperatorService1 == null) {
                        throw new Exception("no bean named 'ftpOperatorService'");
                    }
                    AppFtpUtils.ftpOperatorService = ftpOperatorService1;
                }
            }
        }
        return AppFtpUtils.ftpOperatorService;
    }
}
