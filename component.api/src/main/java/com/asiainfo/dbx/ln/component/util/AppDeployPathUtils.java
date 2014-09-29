package com.asiainfo.dbx.ln.component.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by yanlei on 2014/9/9.
 */
public class AppDeployPathUtils {
    private  static Logger logger = LoggerFactory.getLogger(AppDeployPathUtils.class);

    private static String deployRootDir;
    public static String getDeployRootDir(){
        return AppDeployPathUtils.deployRootDir;
    }
    public  static void setDeployRootDir(String deployRootDir ){
        if(deployRootDir != null) {
            AppDeployPathUtils.deployRootDir = deployRootDir;
            AppDeployPathUtils.deployEtcDir = new File(deployRootDir, "etc").getAbsolutePath();
            AppDeployPathUtils.deployBinDir = new File(deployRootDir, "bin").getAbsolutePath();
            AppDeployPathUtils.deployTempDir = new File(deployRootDir, "temp").getAbsolutePath();
            AppDeployPathUtils.deployTempLibDir = new File(AppDeployPathUtils.deployTempDir, "lib").getAbsolutePath();
            AppDeployPathUtils.deployLibDir = new File(deployRootDir, "lib").getAbsolutePath();
            AppDeployPathUtils.deployWebDir = new File(deployRootDir, "web").getAbsolutePath();
            AppDeployPathUtils.deployLogDir = new File(deployRootDir, "logs").getAbsolutePath();
        }
    }
    private static String deployEtcDir;
    public static String getDeployEtcDir(){
        return AppDeployPathUtils.deployEtcDir;
    }
    private static String deployBinDir;
    public static String getDeployBinDir(){
        return AppDeployPathUtils.deployBinDir;
    }
    private static String deployTempDir;
    public static String getDeployTempDir(){
        return AppDeployPathUtils.deployTempDir;
    }
    private static String deployTempLibDir;
    public static  String getDeployTempLibDir(){
        return AppDeployPathUtils.deployTempLibDir;
    }
    private static String deployLibDir;
    public static String getDeployLibDir(){
        return AppDeployPathUtils.deployLibDir;
    }
    private static String deployWebDir;
    public static String getDeployWebDir(){
        return AppDeployPathUtils.deployWebDir;
    }
    private static String deployLogDir;
    public static String getDeployLogDir(){
        return AppDeployPathUtils.deployLogDir;
    }
}
