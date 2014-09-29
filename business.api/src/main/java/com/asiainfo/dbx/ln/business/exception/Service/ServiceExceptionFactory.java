package com.asiainfo.dbx.ln.business.exception.Service;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;


/**
 * Created by yanlei on 2014/9/1.
 */
public class ServiceExceptionFactory {
    public static ResourceNotFoundException createResourceNotFoundException(ResourceDefine resourceDefine,Exception e){
        return new ResourceNotFoundException(resourceDefine,e);
    }
    public  static ResourceOperatorNotFoundException createResourceOperatorNotFoundException(ResourceDefine resourceDefine,OperationDefine operationDefine,Exception e){
        return new ResourceOperatorNotFoundException(resourceDefine,operationDefine,e);
    }
}
