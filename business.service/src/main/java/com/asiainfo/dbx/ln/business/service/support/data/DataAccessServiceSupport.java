package com.asiainfo.dbx.ln.business.service.support.data;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.ServiceSupport;
import com.asiainfo.dbx.ln.business.service.data.DataAccessServiceFacade;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataAccessServiceSupport implements ServiceSupport {
    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, String jsonStr) throws Exception {
        String jsonstr = (String)jsonStr;
        Map jsonMap = AppJsonUtils.jsonToMap(jsonstr, true);
        return (T)call(resourceDefine,operationDefine,jsonMap);
    }

    @Override
    public <T> T call(ResourceDefine resourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        return (T)this.dataAccessServiceFacade.deal(DataResourceDefine.class.cast(resourceDefine),operationDefine,jsonMap);
    }


    DataAccessServiceFacade dataAccessServiceFacade = null;

    public DataAccessServiceFacade getDataAccessServiceFacade() {
        return dataAccessServiceFacade;
    }

    public void setDataAccessServiceFacade(DataAccessServiceFacade dataAccessServiceFacade) {
        this.dataAccessServiceFacade = dataAccessServiceFacade;
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return desourceDefine.getResourceType().equals(ResourceDefine.RESOURCE_TYPE_DATA) && this.dataAccessServiceFacade != null && this.dataAccessServiceFacade.match(desourceDefine,operationDefine);
    }
}
