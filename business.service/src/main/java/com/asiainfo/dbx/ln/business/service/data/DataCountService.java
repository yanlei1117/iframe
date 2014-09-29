package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.ServiceConstant;
import com.asiainfo.dbx.ln.component.util.AppBeanUtils;

import java.util.Map;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataCountService extends DefaultDataAccessService<Float>{

    @Override
    public Float deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine, Map jsonMap) throws Exception {
        MyBatisMapper myBatisMapper = this.getMyBatisMapper(dataResourceDefine);
        MyBatisExample myBatisExample =  injectMyBatisExample(dataResourceDefine,jsonMap);
        AppBeanUtils.populate(myBatisExample, jsonMap);
        return  myBatisMapper.countByExample(myBatisExample);

    }

    @Override
    public boolean matchOperationDefine(OperationDefine operationDefine) {
        return operationDefine.getOperationName().equals(ServiceConstant.RESOURCE_OPERATOR_TYPE_COUNT);
    }
}
