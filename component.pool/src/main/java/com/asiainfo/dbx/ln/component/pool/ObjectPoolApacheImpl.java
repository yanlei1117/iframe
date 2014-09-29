package com.asiainfo.dbx.ln.component.pool;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.SoftReferenceObjectPool;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/9/15.
 */
public class ObjectPoolApacheImpl implements  ObjectRefPool ,Loader {
    Map<String,ObjectPool>  poolMap = new ConcurrentHashMap<String,ObjectPool>()  ;


    List<String> objectNamelist =Collections.unmodifiableList(Collections.EMPTY_LIST);

    List<ObjectNameIdentiferPoolFactory> objectNameIdentiferPoolFactoryList = null;

    public List<ObjectNameIdentiferPoolFactory> getObjectNameIdentiferPoolFactoryList() {
        return objectNameIdentiferPoolFactoryList;
    }

    public void setObjectNameIdentiferPoolFactoryList(List<ObjectNameIdentiferPoolFactory> objectNameIdentiferPoolFactoryList) {
        this.objectNameIdentiferPoolFactoryList = objectNameIdentiferPoolFactoryList;
    }

    boolean allowFindFactoryByTypeFromSpring = true;

    public boolean isAllowFindFactoryByTypeFromSpring() {
        return allowFindFactoryByTypeFromSpring;
    }

    public void setAllowFindFactoryByTypeFromSpring(boolean allowFindFactoryByTypeFromSpring) {
        this.allowFindFactoryByTypeFromSpring = allowFindFactoryByTypeFromSpring;
    }

    @Override
    public void load() throws Exception {
        List<String> objectNamelist1 =  new ArrayList<String>();
        if(objectNameIdentiferPoolFactoryList != null){
            Iterator<ObjectNameIdentiferPoolFactory> iterator = objectNameIdentiferPoolFactoryList.iterator();
            load(iterator,objectNamelist1);
        }
        if(allowFindFactoryByTypeFromSpring) {
            Map<String, ObjectNameIdentiferPoolFactory> beanMap = AppSpringUtils.getApplicationContextHolder().getBeansOfType(ObjectNameIdentiferPoolFactory.class);
            if (beanMap != null) {
                load(beanMap.values().iterator(),objectNamelist1);
            }
        }
        this.objectNamelist = Collections.unmodifiableList(objectNamelist1);
    }

    private void load( Iterator<ObjectNameIdentiferPoolFactory> iterator, List<String> objectNamelist1){
        if(iterator != null) {
            while (iterator.hasNext()) {
                ObjectNameIdentiferPoolFactory objectNameIdentiferPoolFactory = iterator.next();
                if (objectNameIdentiferPoolFactory != null) {
                    String objectName = objectNameIdentiferPoolFactory.getObjectName();
                    if (objectName != null) {
                        ObjectPool objectPool = new SoftReferenceObjectPool(objectNameIdentiferPoolFactory);
                        poolMap.put(objectName, objectPool);
                        objectNamelist1.add(objectName);
                    }
                }
            }
        }
    }
    public List<String> getObjectNames(){
        return this.objectNamelist;
    }

    @Override
    public Object borrowObject(String objectName) throws Exception, NoSuchElementException, IllegalStateException {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
            return objectPool.borrowObject();
        }
    }

    @Override
    public void returnObject(Object o, String objectName) throws Exception {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
             objectPool.returnObject(o);
        }
    }

    @Override
    public void invalidateObject(Object o, String objectName) throws Exception {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
            objectPool.invalidateObject(o);
        }
    }

    @Override
    public void addObject(String objectName) throws Exception, IllegalStateException, UnsupportedOperationException {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
            objectPool.addObject();
        }
    }

    @Override
    public int getNumIdle(String objectName)  throws Exception{
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
            return objectPool.getNumIdle();
        }
    }

    @Override
    public int getNumActive(String objectName) {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
            return objectPool.getNumActive();
        }
    }

    @Override
    public void clear(String objectName) throws Exception, UnsupportedOperationException {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
             objectPool.clear();
        }
    }

    @Override
    public void close(String objectName) {
        ObjectPool  objectPool =  poolMap.get(objectName);
        if(objectPool == null){
            throw new NoSuchElementException(" no ObjectPool named '"+objectName+"'");
        }else {
            objectPool.close();
        }
    }
}
