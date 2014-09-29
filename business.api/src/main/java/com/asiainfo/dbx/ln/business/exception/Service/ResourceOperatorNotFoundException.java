package com.asiainfo.dbx.ln.business.exception.Service;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;


/**
 * Created by yanlei on 2014/9/1.
 */
public class ResourceOperatorNotFoundException extends  RuntimeException  {
    public ResourceOperatorNotFoundException (ResourceDefine resourceDefine,OperationDefine operationDefine,Exception e) {
        super("resource or  operator not found ,resource:"+ resourceDefine.toString()+" operation:"+operationDefine,e);
    }
}
