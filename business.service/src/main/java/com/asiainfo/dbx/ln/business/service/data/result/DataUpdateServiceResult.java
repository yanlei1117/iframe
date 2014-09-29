package com.asiainfo.dbx.ln.business.service.data.result;

import com.asiainfo.dbx.ln.business.service.data.DataAccessService;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.data.DataUpdateService;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataUpdateServiceResult implements DataAccessService<DataEffectResult>{
    DataUpdateService dataUpdateService ;

    public DataUpdateService getDataUpdateService() {
        return dataUpdateService;
    }

    public void setDataUpdateService(DataUpdateService dataUpdateService) {
        this.dataUpdateService = dataUpdateService;
    }

    @Override
    public DataEffectResult deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        Integer integer = this.dataUpdateService.deal(dataResourceDefine,operationDefine,jsonMap);
        DataEffectResult dataEffectResult = new DataEffectResult(integer);

        return dataEffectResult;

    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return this.dataUpdateService.match(desourceDefine,operationDefine);
    }
}
