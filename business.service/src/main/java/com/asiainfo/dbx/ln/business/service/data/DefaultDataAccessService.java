package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.business.service.*;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.MyBatisResource;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.finder.MyBatisResourceFinder;
import com.asiainfo.dbx.ln.component.util.AppBeanUtils;


import java.util.Map;

/**
 * {"orderByClause":"","limit":"","offset":"","distinct":"true/false","queryParams":{"name":{"$like":"yanlei%"},"$or":{"age":{"$gt":10,"$lt":50}}}}//查询数据库
 * {"modelParams":{"id":"@{UUID}","name":"yanlei","identityNumber":"9919","sex":"body","birthday":"@{SYSDATE}"}}//插入数据库
 *
 * {"modelParams":{"name":"yanlei1"}，queryParams:{"id":"83898943"}}//更新数据库
 *
 * Created by yanlei on 2014/8/27.
 */
public abstract class DefaultDataAccessService<T> implements DataAccessService<T> {

   MyBatisResourceFinder myBatisResourceFinder = null;

    public MyBatisResourceFinder getMyBatisResourceFinder() {
        return myBatisResourceFinder;
    }

    public void setMyBatisResourceFinder(MyBatisResourceFinder myBatisResourceFinder) {
        this.myBatisResourceFinder = myBatisResourceFinder;
    }

    public abstract boolean matchOperationDefine(OperationDefine operationDefine);

    public boolean matchResourceDefine(ResourceDefine desourceDefine){
        return this.myBatisResourceFinder.findResource(desourceDefine) != null;
    }

    @Override
    public boolean match(ResourceDefine resourceDefine, OperationDefine operationDefine) {
        boolean matchResourceDefine =  this.matchResourceDefine(resourceDefine);
        if(matchResourceDefine){
            boolean matchOperationDefine = this.matchOperationDefine(operationDefine);
            return matchOperationDefine;
        }
        return false;
    }

     MyBatisMapper getMyBatisMapper(ResourceDefine resourceDefine){
        return  this.getMyBatisResourceFinder().findResource(resourceDefine).getMyBatisMapper();
    }
     Object getModel(ResourceDefine resourceDefine) throws Exception{
        return  AppBeanUtils.instanceBean(this.getMyBatisResourceFinder().findResource(resourceDefine).getMyBatisModelClass());
    }

     MyBatisExample getMyBatisExample(ResourceDefine resourceDefine) throws Exception {
         return this.getMyBatisResourceFinder().findResource(resourceDefine).createMyBatisExample();
     }

    Object injectModel(DataResourceDefine dataResourceDefine,Map jsonMap) throws Exception{
        Object model = this.getModel(dataResourceDefine);
        if(jsonMap.containsKey(ServiceConstant.MODEL_PRAMETER_NAME)){
            Object obj =  jsonMap.get(ServiceConstant.MODEL_PRAMETER_NAME);
            if(Map.class.isAssignableFrom(obj.getClass())){
                AppBeanUtils.populate(model,(Map)obj);
                return model;
            }else{
                throw new Exception(" value of  the key named '"+ServiceConstant.MODEL_PRAMETER_NAME+"' should be Map,for json:"+jsonMap);
            }
        }else{
            throw new Exception("json parameter should be contain key:"+ServiceConstant.MODEL_PRAMETER_NAME+",for json:"+jsonMap);
        }
    }
    MyBatisExample injectMyBatisExample(DataResourceDefine dataResourceDefine,Map jsonMap) throws Exception{
         MyBatisResource myBatisResource = this.getMyBatisResourceFinder().findResource(dataResourceDefine);;
         MyBatisMapper myBatisMapper =  myBatisResource.getMyBatisMapper();
         Object model = AppBeanUtils.instanceBean(myBatisResource.getMyBatisModelClass());
         MyBatisExample myBatisExample = myBatisResource.createMyBatisExample();

         if(dataResourceDefine.getItemId() != null){
             myBatisExample.andPrimaryKeyEqualTo(dataResourceDefine.getItemId());
         }else {
             if (jsonMap.containsKey(ServiceConstant.QUERY_PRAMETER_NAME)) {
                 Object obj = jsonMap.get(ServiceConstant.QUERY_PRAMETER_NAME);
                 if (Map.class.isAssignableFrom(obj.getClass())) {
                     JsonToExample.toExample((Map) obj, model.getClass(), myBatisExample);
                 } else {
                     throw new Exception(" value of  the key named '" + ServiceConstant.QUERY_PRAMETER_NAME + "' should be Map,for json:" + jsonMap);
                 }
             }
         }
        return myBatisExample;
    }

   /* @Override
    public <T> T call(ResourceDefine resourceDefine,OperationDefine operationDefine, Object requestBody) throws Exception{
        String jsonstr = (String)requestBody;
        Map jsonMap = AppJsonUtils.jsonToMap(jsonstr, true);
        return (T)deal(resourceDefine,operationDefine,jsonMap);
    }*/

}
