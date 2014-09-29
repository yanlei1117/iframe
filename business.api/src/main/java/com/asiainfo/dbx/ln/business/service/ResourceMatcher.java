package com.asiainfo.dbx.ln.business.service;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

/**
 * Created by yanlei on 2014/9/13.
 */
public interface ResourceMatcher {
    public boolean match(ResourceDefine resourceDefine, OperationDefine operationDefine);
}
