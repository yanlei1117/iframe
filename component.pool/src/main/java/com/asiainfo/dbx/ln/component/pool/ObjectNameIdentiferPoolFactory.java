package com.asiainfo.dbx.ln.component.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;

/**
 * Created by yanlei on 2014/9/15.
 */
public abstract class ObjectNameIdentiferPoolFactory<T> extends BasePooledObjectFactory<T> {
    public  abstract  String getObjectName();
}
