package com.asiainfo.dbx.ln.business.service.support.procedure;

import com.asiainfo.dbx.ln.business.service.ServiceSupport;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.util.AppRunnerUtils;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/25.
 */
public class ProcedureServiceSupport implements ServiceSupport {
    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, Map jsonMap) throws Throwable {
        String uuid = AppStringUtils.getUUID();
        AppVarUtils.getVarContainer(ProcedureServiceSupport.class).setVar(uuid,jsonMap);
        Object obj  =  AppRunnerUtils.getRunnerContainer(ProcedureServiceSupport.class).runProcedure((ProcedureResourceDefine) resourceDefine, uuid,null);
        return (T)obj;
    }

    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, String jsonStr) throws Exception {
        Map jsonMap = AppJsonUtils.jsonToMap(jsonStr, true);
        return call(resourceDefine,operationDefine,jsonStr);
    }

    @Override
    public boolean match(ResourceDefine resourceDefine, OperationDefine operationDefine) {
        return resourceDefine.getResourceType().equals(ResourceDefine.RESOURCE_TYPE_PROCEDURE);
    }
}
