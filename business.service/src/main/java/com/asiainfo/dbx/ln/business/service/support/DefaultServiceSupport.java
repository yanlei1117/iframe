package com.asiainfo.dbx.ln.business.service.support;

import com.asiainfo.dbx.ln.business.exception.Service.ServiceExceptionFactory;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.ServiceSupport;


import java.util.*;

/**
 * Created by yanlei on 2014/9/1.
 */
public class DefaultServiceSupport<T> implements ServiceSupport {

    List<ServiceSupport> serviceSupportList ;

    public List<ServiceSupport> getServiceSupportList() {
        return serviceSupportList;
    }

    public void setServiceSupportList(List<ServiceSupport> serviceSupportList) {
        this.serviceSupportList = serviceSupportList;
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return findServiceSupportForResourceOperation(desourceDefine,operationDefine) != null;
    }
    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, String jsonStr) throws Throwable {
        ServiceSupport serviceSupport   =  findServiceSupportForResourceOperation(resourceDefine,operationDefine);
        if(serviceSupport != null) {
            T t = (T)serviceSupport.call(resourceDefine,operationDefine, jsonStr);
            return t;
        }else{
            throw ServiceExceptionFactory.createResourceOperatorNotFoundException(resourceDefine,operationDefine,null);
        }
    }
    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, Map jsonMap) throws Throwable {
        ServiceSupport serviceSupport   =  findServiceSupportForResourceOperation(resourceDefine,operationDefine);
        if(serviceSupport != null) {
            T t = (T)serviceSupport.call(resourceDefine,operationDefine, jsonMap);
            return t;
        }else{
            throw ServiceExceptionFactory.createResourceOperatorNotFoundException(resourceDefine,operationDefine,null);
        }

    }


    private ServiceSupport findServiceSupportForResourceOperation(ResourceDefine resourceDefine,OperationDefine operationDefine){
        if(serviceSupportList != null){
            for(ServiceSupport serviceSupport:serviceSupportList){
                if(serviceSupport.match(resourceDefine,operationDefine)){
                    return serviceSupport;
                }
            }
        }
        return null;
    }
}
