package com.asiainfo.dbx.ln.business.service.data.result;

import com.asiainfo.dbx.ln.business.service.data.DataAccessService;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.data.DataCountService;
import com.asiainfo.dbx.ln.business.service.data.DataReadService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataReadServiceResult implements DataAccessService<Object> {

    private DataReadService dataReadService ;

    public DataReadService getDataReadService() {
        return dataReadService;
    }

    public void setDataReadService(DataReadService dataReadService) {
        this.dataReadService = dataReadService;
    }

    private DataCountService dataCountService = null;

    public DataCountService getDataCountService() {
        return dataCountService;
    }

    public void setDataCountService(DataCountService dataCountService) {
        this.dataCountService = dataCountService;
    }

    @Override
    public Object deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        List objList =   this.dataReadService.deal(dataResourceDefine,operationDefine,jsonMap);
        if(jsonMap != null) {
            Integer limit = (String) jsonMap.get("limit") == null ? null : Integer.parseInt((String) jsonMap.get("limit"));
            Integer offset = (String) jsonMap.get("offset") == null ? null : Integer.parseInt((String) jsonMap.get("offset"));
            if (limit != null) {
                DataReadPageResult dataReadResult = new DataReadPageResult();
                dataReadResult.setDataList(objList);
                dataReadResult.setReadNum(objList.size());
                dataReadResult.setTotalNum(objList.size());
                dataReadResult.setLimit(limit.intValue());
                dataReadResult.setOffset(offset == null ? 0 : offset.intValue());
                if (objList.size() > 0) {
                    Float total = this.dataCountService.deal(dataResourceDefine, operationDefine, jsonMap);
                    dataReadResult.setTotalNum(total.intValue());
                }
                return dataReadResult;
            }else{
                DataReadResult dataReadResult = new DataReadResult();
                dataReadResult.setReadNum(objList.size());
                dataReadResult.setDataList(objList);
                return dataReadResult;
            }

        }else{
            DataReadOneResult dataReadOneResult  = new DataReadOneResult();
            dataReadOneResult.setReadNum(objList.size());
            dataReadOneResult.setDataModel(objList.size()==0? Collections.emptyMap():objList.get(0));
            return dataReadOneResult;
        }
    }

    @Override
    public boolean match(ResourceDefine resourceDefine, OperationDefine operationDefine) {
        return this.dataReadService.match(resourceDefine,operationDefine);
    }
}
