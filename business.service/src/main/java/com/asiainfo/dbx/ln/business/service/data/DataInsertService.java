package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

import com.asiainfo.dbx.ln.business.service.ServiceConstant;

import java.util.Map;

/**
 * Created by yanlei on 2014/8/29.
 */
public class DataInsertService extends DefaultDataAccessService<Object> {


    @Override
    public boolean matchOperationDefine(OperationDefine operationDefine) {
        return operationDefine.getOperationName().equals(ServiceConstant.RESOURCE_OPERATOR_TYPE_CREATE);
    }

    @Override
    public Object deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        MyBatisMapper myBatisMapper =  this.getMyBatisMapper(dataResourceDefine);
        Object model = this.injectModel(dataResourceDefine,jsonMap);
        int effectRecords =   myBatisMapper.insertSelective(model);
        if(effectRecords ==1){
            return model;
        }else{
            throw new Exception(" insert to '"+model.getClass().getName()+"',effect record:{"+effectRecords+"},for json:"+jsonMap);
        }

    }
}
