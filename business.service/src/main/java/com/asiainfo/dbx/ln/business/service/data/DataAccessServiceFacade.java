package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataAccessServiceFacade<T> implements DataAccessService{
    List<DataAccessService> dataAccessServiceList = null;

    public List<DataAccessService> getDataAccessServiceList() {
        return dataAccessServiceList;
    }

    public void setDataAccessServiceList(List<DataAccessService> dataAccessServiceList) {
        this.dataAccessServiceList = dataAccessServiceList;
    }

    @Override
    public T deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        DataAccessService dataAccessService = findMatchDataAccessService(dataResourceDefine,operationDefine);
        return (T)dataAccessService.deal(dataResourceDefine,operationDefine,jsonMap);
    }

    @Override
    public boolean match(ResourceDefine desourceDefine, OperationDefine operationDefine) {
        DataAccessService das =  findMatchDataAccessService(desourceDefine,operationDefine);
        return das != null;
    }

    private DataAccessService findMatchDataAccessService(ResourceDefine desourceDefine, OperationDefine operationDefine){
        if(this.dataAccessServiceList != null && this.dataAccessServiceList.size()>0){
            for(DataAccessService dataAccessService:this.dataAccessServiceList){
                if(dataAccessService.match(desourceDefine,operationDefine)){
                    return dataAccessService;
                }
            }
        }
        return null;
    }
}
