package com.asiainfo.dbx.ln.component.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by yanlei on 2014/9/10.
 */
public class BootBin {
    private final static Logger logger = LoggerFactory.getLogger(BootBin.class);

    public static  void bootBin() throws Exception {

        AppDeployPathUtils.findDeployRootDir();
        ClassLoader classLoader = ClassLoaderFactory.createCustomClassLoader(new String []{AppDeployPathUtils.getDeployLibDir(), AppDeployPathUtils.getDeployTempLibDir()}, AppDeployPathUtils.getDeployEtcDir());
        Thread.currentThread().setContextClassLoader(classLoader);
        Class  appComponentClass = classLoader.loadClass("com.asiainfo.dbx.ln.component.api.AppComponent");
        Method method = appComponentClass.getMethod("startBin",String.class,ClassLoader.class);
        method.invoke(null,AppDeployPathUtils.getDeployRootDir(),classLoader);

    }
    public static void main(String [] args){
        try{
              new BootBin().bootBin();
        }catch (Throwable e){
            logger.error("",e);
            System.exit(1);
        }
    }
}
