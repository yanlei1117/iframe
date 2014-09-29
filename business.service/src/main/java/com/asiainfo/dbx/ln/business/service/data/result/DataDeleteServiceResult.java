package com.asiainfo.dbx.ln.business.service.data.result;

import com.asiainfo.dbx.ln.business.service.data.DataAccessService;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.data.DataDeleteService;

import java.util.Map;

/**
 * Created by Administrator on 2014/9/13.
 */
public class DataDeleteServiceResult implements DataAccessService<DataEffectResult> {
    DataDeleteService dataDeleteService;

    public DataDeleteService getDataDeleteService() {
        return dataDeleteService;
    }

    public void setDataDeleteService(DataDeleteService dataDeleteService) {
        this.dataDeleteService = dataDeleteService;
    }

    @Override
    public DataEffectResult deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
       Integer integer=  this.dataDeleteService.deal(dataResourceDefine,operationDefine,jsonMap);
        DataEffectResult dataEffectResult = new DataEffectResult(integer);

        return dataEffectResult;
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return this.dataDeleteService.match(desourceDefine,operationDefine);
    }
}
