package com.asiainfo.dbx.ln.business.service;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

import java.util.Map;

/**
 * Created by yanlei on 2014/8/27.
 */
public interface ServiceSupport extends ResourceMatcher {

    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, Map map) throws Throwable;
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, String jsonStr) throws Throwable;
}
