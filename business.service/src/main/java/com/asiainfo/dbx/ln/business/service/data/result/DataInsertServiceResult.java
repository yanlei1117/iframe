package com.asiainfo.dbx.ln.business.service.data.result;

import com.asiainfo.dbx.ln.business.service.data.DataAccessService;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.data.DataInsertService;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataInsertServiceResult implements DataAccessService<DataEffectResult> {
   private DataInsertService dataInsertService;

    public DataInsertService getDataInsertService() {
        return dataInsertService;
    }

    public void setDataInsertService(DataInsertService dataInsertService) {
        this.dataInsertService = dataInsertService;
    }

    @Override
    public DataEffectResult  deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        Object obj = this.dataInsertService.deal(dataResourceDefine,operationDefine,jsonMap);
        DataEffectResult dataEffectResult = new DataEffectResult(1);
        dataEffectResult.setEffectModel(obj);
        return dataEffectResult;
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return dataInsertService.match(desourceDefine,operationDefine);
    }
}
