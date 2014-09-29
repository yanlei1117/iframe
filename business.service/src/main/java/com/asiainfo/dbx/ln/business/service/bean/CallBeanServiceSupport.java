package com.asiainfo.dbx.ln.business.service.bean;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.ServiceSupport;

import java.util.Map;

/**
 * Created by Administrator on 2014/9/17.
 */
public class CallBeanServiceSupport implements ServiceSupport {
    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, Map map) throws Exception {
        return null;
    }

    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, String jsonStr) throws Exception {
        return null;
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return false;
    }
}
