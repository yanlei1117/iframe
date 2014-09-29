package com.asiainfo.dbx.ln.component.boot;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.CompoundEnumeration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于加载ETC目录下的资源文件
 * 如spring 中配置为 classpath:/config/db/db.properties ，如打在jar包中不方便修改
 * 因此把类似的资源文件放到某个目录中，如etc ，它可以有N个业务域子目录，子目录为类路径的
 * 根，存放相当的资源文件，如etc/common 这个是通用的资源文件类路径。
 * 如文件：etc/common/config/db/db.properties ，根类路径是由etc/common,类路径为/config/db.properties
 *
 * 在程序启动时，需要首先调用loadAllEtcResourceFile方法，加载etc/子目录下所有类资源文件，并
 * 将ResourceClassLoader设类线程中默认的ClassLader.
 *
 * 当其它类调用类资源文件时，这个类的findResource(s)被触发并返对应资源文件的URL
 *
 * Created by yanlei on 2014/8/25.
 */
public class CustomClassLoader extends  URLClassLoader {
    private final Logger logger = LoggerFactory.getLogger(CustomClassLoader.class);
    public CustomClassLoader (URL [] libUrls,String resourceFileDir [] ) throws Exception{
        super(libUrls,Thread.currentThread().getContextClassLoader());
        this.classPathMap = this.loadAllResourceFile(resourceFileDir);
    }



    @Override
    public URL findResource(String name) {
        URL url = classPathMap.get(name);//资源文件，资源文件目录中有，就不会取jar包中的资源文件
        return url !=null?url:super.findResource(name);
    }

    /**
     *
     * 资源文件为jar包中的资源文件+资源文件目录中的资源文件
     *
     **/
    @Override
    public Enumeration<URL> findResources(final String name) throws IOException {


        Enumeration[] tmp = new Enumeration[2];
        tmp[0]= super.findResources(name);
        tmp[1] = findResourcesByName(name);
        return  new CompoundEnumeration<URL>(tmp);
    }
    private Enumeration<URL> getResourceByName1(final String name) throws Exception{
        final Enumeration<URL> parentUrlEnum = super.findResources(name);
        final  Iterator<String> iteartor =  classPathMap.keySet().iterator();
        Enumeration urlEmnum =   new Enumeration<URL>(){
            URL currentURL = null;
            @Override
            public boolean hasMoreElements() {
                while(iteartor.hasNext()){
                    String   classPath = iteartor.next();
                    if(classPath.startsWith(name)){
                        currentURL = classPathMap.get(classPath);
                        return true;
                    }
                }
                if(parentUrlEnum != null && parentUrlEnum.hasMoreElements()){
                    currentURL = parentUrlEnum.nextElement();
                    logger.info("return true;currentURL:{}",currentURL);

                    return true;
                }
                logger.info("return false;currentURL:{}",currentURL);
                currentURL = null;
                return false;
            }

            @Override
            public URL nextElement() {
                logger.info("currentURL:{}",currentURL);
                return currentURL;
            }
        };
        return urlEmnum;
    }
    private Enumeration<URL> findResourcesByName(String resouceName){
        List<URL> list = new ArrayList<URL>();
        final  Iterator<String> iteartor =  classPathMap.keySet().iterator();
        while(iteartor.hasNext()){
            String   classPath = iteartor.next();
            if(classPath.startsWith(resouceName)){
               URL resouceURL = classPathMap.get(classPath);
               if(resouceURL != null){
                   list.add(resouceURL);
               }
            }
        }
        return  Collections.enumeration(list);
    }
    private Map<String,URL> classPathMap  = new ConcurrentHashMap<String, URL>();


    public  Map<String,URL>    loadAllResourceFile(String resourceFileDirs[]) throws Exception{
        Map<String,URL> classPathMap  = new ConcurrentHashMap<String, URL>();;
        if(resourceFileDirs != null && resourceFileDirs.length>0){
            for(String filePath:resourceFileDirs){
                loadResources(new File(filePath),"",classPathMap);
            }

        }
        return classPathMap;
    }


    private void loadResources(File file,String currentClassPath,Map<String,URL> classPathMap) throws Exception{

        if(currentClassPath != null && currentClassPath.trim().length()>0) {
            classPathMap.put(currentClassPath, file.toURI().toURL());
        }
        if(file.isDirectory()){
            File [] files = file.listFiles();
            if(files != null && files.length>0){
                for( File subFile:files){
                    loadResources(subFile, (currentClassPath != null && currentClassPath.trim().length()>0)?currentClassPath+"/"+subFile.getName():subFile.getName(), classPathMap);
                }
            }
        }
    }





}
