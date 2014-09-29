package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;


import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.ServiceConstant;

import com.asiainfo.dbx.ln.component.util.AppBeanUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/8/27.
 */
public class DataReadService extends DefaultDataAccessService<List> {
    JsonToExample jsonToExample = new JsonToExample();



    @Override
    public boolean matchOperationDefine(OperationDefine operationDefine) {
        return operationDefine.getOperationName().equals(ServiceConstant.RESOURCE_OPERATOR_TYPE_READ);
    }

    @Override
     public   List deal(DataResourceDefine dataResourceDefine, OperationDefine operationDefine,   Map jsonMap) throws Exception{

        MyBatisMapper myBatisMapper = this.getMyBatisMapper(dataResourceDefine);
        MyBatisExample myBatisExample =  injectMyBatisExample(dataResourceDefine,jsonMap);
        AppBeanUtils.populate(myBatisExample, jsonMap);
        List<Object> objList   = myBatisMapper.selectByExample(myBatisExample);


        return objList;
    }
}
