package com.asiainfo.dbx.ln.business.service.resource.finder;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.MyBatisResource;
import com.asiainfo.dbx.ln.component.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/8/27.
 */
public   class MyBatisResourceFinder  implements  ResourceFinder<MyBatisResource>,Loader {
    private Logger logger = LoggerFactory.getLogger(MyBatisResourceFinder.class);
    String basePackage= MyBatisMapper.DEFAULT_MODEL_PACKAGE;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    Map<ResourceDefine,MyBatisResource> myBatisResourceMap = new ConcurrentHashMap<ResourceDefine,MyBatisResource >();

    @Override
    public void load() throws  Exception{
       Map<String,MyBatisMapper> myBatisMapperMap = AppSpringUtils.getApplicationContextHolder().getBeansOfType(MyBatisMapper.class);
       Iterator<String> iterator = myBatisMapperMap.keySet().iterator();
        while(iterator.hasNext()){
            String beanName = iterator.next();
            MyBatisMapper mapper = myBatisMapperMap.get(beanName);
          //  MapperProxy mapperProxy = (MapperProxy)mapper;
            Class mapperClass = null;
            if(Proxy.isProxyClass(mapper.getClass())){
                InvocationHandler invocationHandler =  Proxy.getInvocationHandler(mapper);
                Field   mapperInterfaceField  = AppFieldUtils.getField(invocationHandler.getClass(),"mapperInterface",true);
                mapperClass = (Class)mapperInterfaceField.get(invocationHandler);
                System.out.println();
            }else{
                mapperClass = mapper.getClass();

            }

            Class modelClass = AppClassUtils.getInterfaceGenericTypes(mapperClass,MyBatisMapper.class,0);
            String modelFullName = modelClass.getName();
            String exampleClassName= modelFullName+"Example";
            Class myBatisExampleClass = AppClassUtils.instanceClass(exampleClassName);
            MyBatisResource myBatisResource = new  MyBatisResource(mapper,modelClass,myBatisExampleClass);
            String modelName = modelClass.getSimpleName();
            String className = modelClass.getName();
            String packages[] = className.split("\\.");
            String repository = packages[packages.length-3];
            String container = packages[packages.length-2];
            String collection= packages[packages.length-1];
            ResourceDefine resourceDefine = ResourceDefineFactory.createDataResourceDefine(repository,container,collection);
            myBatisResourceMap.put(resourceDefine,myBatisResource);
            logger.info("find MyBatisResource :{},the ResourceDefine matched:{}",myBatisResource,resourceDefine);
        }
    }

    @Override
    public MyBatisResource findResource(ResourceDefine resourceDefine)  {
            return myBatisResourceMap.get(resourceDefine);

    }
}
