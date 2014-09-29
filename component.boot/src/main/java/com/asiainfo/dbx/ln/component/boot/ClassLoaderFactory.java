package com.asiainfo.dbx.ln.component.boot;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by yanlei on 2014/8/26.
 */
public class ClassLoaderFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClassLoaderFactory.class);
    public static ClassLoader  createCustomClassLoader(String libDir[], String resourceParentDir ) throws Exception{
        URL[] urls = getAllLibURL(libDir);
        log(urls,"add jar to classpath:\n{}");
        String [] resourceDir = getResourceFileDir(resourceParentDir);
        log(resourceDir,"add resourceDir to classpath:\n{}");
        ClassLoader classLoader = new CustomClassLoader(urls,resourceDir);
        return classLoader;
    }

    private static void log(Object [] data,String message){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<data.length;i++){
            sb.append(data[i]);
            sb.append("\n");
        }
        logger.info(message,sb.toString());
    }

    private static  String [] getResourceFileDir(String resourceParentDir) throws Exception{
        if(resourceParentDir != null && resourceParentDir.trim().length()>0){
            File file = new File(resourceParentDir);
            if(file.isFile()){
                throw  new IllegalArgumentException("resourceParentDir is not a directory");
            }else{
                File[] files =  file.listFiles();
                List<String> fileList = new ArrayList<String>();
                for(File subFile:files){
                    if(subFile.isDirectory()){
                        fileList.add(subFile.getAbsolutePath());
                    }
                }
                if(fileList.size()>0) {
                    String[] resurceDirs = new String[fileList.size()];
                    for (int i = 0; i < fileList.size(); i++) {
                        resurceDirs[i] = fileList.get(i);
                    }
                    return resurceDirs;
                }

            }
        }
        return null;
    }
    private static URL[] getAllLibURL(String libDirs[]) throws Exception{
        URL [] urls = null;
        if(libDirs != null && libDirs.length>0){
            List<URL> urlList = new ArrayList<URL>();
            for(String libDir :libDirs) {
                if(libDir != null && libDir.trim().length()>0) {

                    File file = new File(libDir);
                    if (file.isDirectory()) {
                        File[] subFiles = file.listFiles();
                        if (subFiles != null && subFiles.length>0) {

                            for (File subFile : subFiles) {
                                if (subFile.isFile()) {
                                    urlList.add(subFile.toURI().toURL());
                                }
                            }
                        }
                    }else{
                        urlList.add(file.toURI().toURL());
                    }
                }
            }
            if(urlList.size()>0) {
                urls = new URL[urlList.size()];
                for (int i = 0; i < urlList.size(); i++) {
                    urls[i] = urlList.get(i);
                }
            }
        }

        return urls;
    }

}
