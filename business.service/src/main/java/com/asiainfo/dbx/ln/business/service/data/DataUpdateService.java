package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

import com.asiainfo.dbx.ln.business.service.ServiceConstant;

import java.util.Map;

/**
 * Created by yanlei on 2014/8/29.
 */
public class DataUpdateService extends DefaultDataAccessService<Integer> {

    @Override
    public boolean matchOperationDefine(OperationDefine operationDefine) {
        return operationDefine.getOperationName().equals(ServiceConstant.RESOURCE_OPERATOR_TYPE_UPDATE);
    }

    @Override
    public Integer deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        MyBatisMapper myBatisMapper = this.getMyBatisMapper(dataResourceDefine);
        MyBatisExample myBatisExample = this.injectMyBatisExample(dataResourceDefine,jsonMap);
        Object model = this.injectModel(dataResourceDefine,jsonMap);
        int effectRecords = myBatisMapper.updateByExampleSelective(model, myBatisExample);
        return effectRecords;
    }

}
