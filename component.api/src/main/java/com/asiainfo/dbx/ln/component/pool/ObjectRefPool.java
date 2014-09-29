package com.asiainfo.dbx.ln.component.pool;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by yanlei on 2014/9/15.
 */
public interface ObjectRefPool {

    public List<String> getObjectNames();
    public Object borrowObject(String objectName) throws Exception, NoSuchElementException, IllegalStateException;

    
    public void returnObject(Object o,String objectName) throws Exception ;

    
    public void invalidateObject(Object o,String objectName) throws Exception ;

    
    public void addObject(String objectName) throws Exception, IllegalStateException, UnsupportedOperationException;

    
    public int getNumIdle(String objectName) throws Exception;

    
    public int getNumActive(String objectName) throws Exception;

    
    public void clear(String objectName) throws Exception, UnsupportedOperationException;

    
    public void close(String objectName) throws Exception;
}
