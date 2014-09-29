package com.asiainfo.dbx.ln.component.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * Created by yanlei on 2014/8/26.
 */
public class AppDeployPathUtils {
    private  static Logger logger = LoggerFactory.getLogger(AppDeployPathUtils.class);

    private static String deployRootDir;
    public static String getDeployRootDir(){
        return AppDeployPathUtils.deployRootDir;
    }
    private static void setDeployRootDir(String deployRootDir ){
        AppDeployPathUtils.deployRootDir = deployRootDir;
        logger.info("deployRootDir:{}",AppDeployPathUtils.deployRootDir);
        AppDeployPathUtils.deployEtcDir = new File(deployRootDir,"etc").getAbsolutePath();
        logger.info("deployEtcDir:{}",AppDeployPathUtils.deployEtcDir);
        AppDeployPathUtils.deployBinDir = new File(deployRootDir,"bin").getAbsolutePath();
        logger.info("deployBinDir:{}",AppDeployPathUtils.deployBinDir);
        AppDeployPathUtils.deployTempDir = new File(deployRootDir,"temp").getAbsolutePath();
        logger.info("deployTempDir:{}",AppDeployPathUtils.deployTempDir);
        AppDeployPathUtils.deployTempLibDir = new File(AppDeployPathUtils.deployTempDir,"lib").getAbsolutePath();
        AppDeployPathUtils.deployLibDir = new File(deployRootDir,"lib").getAbsolutePath();
        logger.info("deployLibDir:{}",AppDeployPathUtils.deployLibDir);
        AppDeployPathUtils.deployWebDir = new File(deployRootDir, "web").getAbsolutePath();
        logger.info("deployWebDir:{}",AppDeployPathUtils.deployWebDir);

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
        return AppDeployPathUtils.getDeployEtcDir()+File.separator+"temp";
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


    public static  void findDeployRootDir() throws Exception{
        String deployRootDir = System.getProperty("deployRootDir");
        if(deployRootDir == null){
            logger.debug("system property  named 'deployRootDir' not config.try to find 'deployRootDir' by classpath ");
            String classPath = AppDeployPathUtils.class.getName().replaceAll("\\.","/")+".class";
            URL url = AppDeployPathUtils.class.getClassLoader().getResource(classPath);
            logger.debug("findResource:{}-->foundFilePath:{}",classPath,url);
            if(url != null){
                String filePath = url.getFile();
                if(filePath.indexOf(".jar")!= -1){
                    filePath = filePath.substring(0,filePath.indexOf(".jar")+4);
                }
                if(filePath.startsWith("file:/")){
                    filePath= filePath.substring(6);
                }
                logger.debug(" foundFilePath:{}",filePath);
                deployRootDir = findDeployRootDir(new File(filePath));
            }

        }
       // deployRootDir="d:/app/IdeaProjects/component.root/deploy/";
        if(deployRootDir == null){
            throw new Exception(" not found  'deployRootDir' ,please config system property named 'deployRootDir'");
        }else{

            AppDeployPathUtils.setDeployRootDir(deployRootDir);
        }
    }

    public static String findDeployRootDir(File targetFile ){
        if(targetFile.isDirectory()){
            File [] files = targetFile.listFiles();
            int matchNum =0;
            if(files != null &&files.length>0){
                for(File file:files){
                    if(file.isDirectory()){
                        if(file.getName().equals("bin")){
                            matchNum++;
                        }else  if(file.getName().equals("etc")){
                            matchNum++;
                        }else  if(file.getName().equals("temp")){
                            matchNum++;
                        }else  if(file.getName().equals("lib")){
                            matchNum++;
                        }
                    }
                }
            }
            if(matchNum == 4){
                return targetFile.getAbsolutePath();
            }
        }

        if(targetFile.getParentFile() != null){
            return findDeployRootDir(targetFile.getParentFile());
        }
        return null;
    }
}
