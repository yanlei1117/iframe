package com.asiainfo.dbx.ln.component.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by yanlei on 2014/9/10.
 */
public class AppEnvironment {
    private static Logger logger = LoggerFactory.getLogger(AppEnvironment.class);
    public static boolean isFormalEnvironment(){
        return AppDeployPathUtils.getDeployRootDir() != null;
    }
    public static boolean isDevelopEnvironment(){
        return AppDeployPathUtils.getDeployRootDir() == null;
    }
    public static void initFormalEnvironment(){
        if(AppEnvironment.isFormalEnvironment()){
            String logFilePath = AppDeployPathUtils.getDeployLogDir()+File.separator;
            logger.info("set system property:LOG.FILE_PATH={}",logFilePath);
            System.setProperty("LOG.FILE_PATH",AppDeployPathUtils.getDeployLogDir());
        }
    }
}
