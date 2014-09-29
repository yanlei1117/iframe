package com.asiainfo.dbx.ln.component.util;




/**
 * Created by yanlei on 2014/8/26.
 */
public class AppClassLoaderUtils {
    private static ClassLoader classLoader = null;

    public static void  setCurrentClassLoader(ClassLoader classLoader){
        AppClassLoaderUtils.classLoader = classLoader;
        Thread.currentThread().setContextClassLoader(classLoader);
    }
    public static ClassLoader  getCurrentClassLoader(){
        return AppClassLoaderUtils.classLoader==null?Thread.currentThread().getContextClassLoader():AppClassLoaderUtils.classLoader;
    }


}
