package com.asiainfo.dbx.ln.business.service.data.result;

import com.asiainfo.dbx.ln.business.service.data.DataAccessService;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.data.DataCountService;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataCountServiceResult implements DataAccessService<DataCountResult>{
    DataCountService dataCountService = null;

    public DataCountService getDataCountService() {
        return dataCountService;
    }

    public void setDataCountService(DataCountService dataCountService) {
        this.dataCountService = dataCountService;
    }

    @Override
    public DataCountResult deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        Float countResult = this.dataCountService.deal(dataResourceDefine,operationDefine,jsonMap);
        return new DataCountResult(countResult);
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        return this.dataCountService.match(desourceDefine,operationDefine);
    }
}
