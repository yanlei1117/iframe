package com.asiainfo.dbx.ln.component.api;


import com.asiainfo.dbx.ln.component.spring.ApplicationContextHolder;
import com.asiainfo.dbx.ln.component.spring.impl.ApplicationContextHolderParentFixed;
import com.asiainfo.dbx.ln.component.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class AppComponent
{

    private static Logger logger = LoggerFactory.getLogger(AppComponent.class);
    public static void start() throws Exception{
        init();
        runLoader();
    }
    private static void  initDeploy(String deployRootDir,ClassLoader classLoader){
        AppDeployPathUtils.setDeployRootDir(deployRootDir);
        if(classLoader != null){
            AppClassLoaderUtils.setCurrentClassLoader(classLoader);
        }
        if(AppEnvironment.isFormalEnvironment()){
            logger.info("isFormalEnvironment?--->true");
            AppEnvironment.initFormalEnvironment();
        }
    }
    public static  void startBin(String deployRootDir,ClassLoader classLoader) throws Exception {
        initDeploy(deployRootDir,classLoader);
        start();
    }
    public static  void startWebServer(String deployRootDir,ClassLoader classLoader) throws Exception {
        initDeploy(deployRootDir,classLoader);
        WebServer webServer = new WebServer();
        webServer.start();
    }

    public static  void init()throws Exception{
        initConverter();
        initSpring();
    }
    public static void initConverter(){
        AppTypeConvertUtils.initConverter();//初始化日期格式转换
    }
    public static void initSpring() throws Exception{
        AppSpringUtils.initDefaultApplicationContextHolder();
    }

    private static void runLoader() throws Exception{
      Map<String,Loader > loaderMap =  AppSpringUtils.getApplicationContextHolder().getBeansOfType(Loader.class);
      if(loaderMap != null && loaderMap.size() >0){
          Iterator<Loader> iterator = loaderMap.values().iterator();
          while(iterator.hasNext()){
              Loader loader = iterator.next();
              logger.info(loader.getClass().getName()+"-->toLoad");
              loader.load();
          }
      }
    }
}
