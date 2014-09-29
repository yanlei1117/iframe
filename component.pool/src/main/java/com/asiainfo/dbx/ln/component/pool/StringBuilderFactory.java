package com.asiainfo.dbx.ln.component.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by yanlei on 2014/9/15.
 */
public class StringBuilderFactory  extends ObjectNameIdentiferPoolFactory<StringBuilder> {
    @Override
    public String getObjectName() {
        return StringBuilder.class.getName();
    }

    @Override
    public StringBuilder create() throws Exception {
        return   new StringBuilder();
    }

    @Override
    public PooledObject<StringBuilder> wrap(StringBuilder obj) {
        return new DefaultPooledObject<StringBuilder>(obj);

    }
    @Override
    public void passivateObject(PooledObject<StringBuilder> pooledObject) {
        pooledObject.getObject().setLength(0);
    }
}
