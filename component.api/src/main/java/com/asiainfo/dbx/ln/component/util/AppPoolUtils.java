package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.json.JsonMapper;
import com.asiainfo.dbx.ln.component.pool.ObjectRefPool;

/**
 * Created by yanlei on 2014/9/15.
 */
public class AppPoolUtils {
    private volatile  static ObjectRefPool objectRefPool;
    public static ObjectRefPool getObjectRefPool(Class classz) throws Exception{
        if(objectRefPool == null){
            synchronized (AppJsonUtils.class){
                if(objectRefPool ==null) {
                    ObjectRefPool objectRefPool1 = (ObjectRefPool) AppSpringUtils.getApplicationContextHolder().getBean("objectRefPool");
                    if (objectRefPool1 == null) {
                        throw new Exception("no bean named 'objectRefPool'");
                    }
                    objectRefPool = objectRefPool1;
                }
            }
        }
        return objectRefPool;
    }

}
