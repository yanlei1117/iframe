package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.business.service.ResourceMatcher;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;

import java.util.Map;

/**
 * Created by yanlei on 2014/8/14.
 */
public interface DataAccessService<T> extends ResourceMatcher {
    public abstract   T deal(DataResourceDefine DataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception;

}
