package com.asiainfo.dbx.ln.component.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.HashMap;

/**
 * Created by yanlei on 2014/9/15.
 */
public class HashMapFactory extends ObjectNameIdentiferPoolFactory<HashMap> {
    @Override
    public String getObjectName() {
        return HashMap.class.getName();
    }

    @Override
    public HashMap create() throws Exception {
        return new HashMap();
    }

    @Override
    public PooledObject<HashMap> wrap(HashMap obj) {
        return new DefaultPooledObject<HashMap>(obj);
    }

    @Override
    public void passivateObject(PooledObject<HashMap> pooledObject) throws Exception {
        pooledObject.getObject().clear();
    }
}
