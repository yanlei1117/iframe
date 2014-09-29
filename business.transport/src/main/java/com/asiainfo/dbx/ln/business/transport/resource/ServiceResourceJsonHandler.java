package com.asiainfo.dbx.ln.business.transport.resource;

import com.asiainfo.dbx.ln.business.service.ServiceSupport;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.transport.http.rest.HttpJsonResult;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * Created by yanlei on 2014/9/25.
 */
public class ServiceResourceJsonHandler extends   AutoInjectResourceJsonHandler{
    private final static Logger logger = LoggerFactory.getLogger(ServiceResourceJsonHandler.class);
    @Resource(name="defaultServiceSupportResult")
    ServiceSupport serviceSupport;

    public ServiceSupport getServiceSupport() {
        return serviceSupport;
    }

    public void setServiceSupport(ServiceSupport serviceSupport) {
        this.serviceSupport = serviceSupport;
    }

    @Override
    public ResponseEntity dealRequest(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine,  String requestBody,ServletWebRequest servletWebRequest) {

        try {
            Object obj = this.getServiceSupport().call((DataResourceDefine) resourceDefine, operationDefine, requestBody);
            HttpJsonResult httpJsonResult = new HttpJsonResult();
            httpJsonResult.setResult(obj);
            String jsonResponseStr = AppJsonUtils.objToJson(httpJsonResult);
            ResponseEntity responseEntity = new ResponseEntity(jsonResponseStr, HttpStatus.OK);
            return responseEntity;
        }catch(Throwable e){
            String failCode = AppStringUtils.getSequenceCode()+"";
            logger.error("failCode:{}",failCode,e);
            logger.error("DataResourceHandler error (domain:{},resourceDefine:{},operationDefine:{},requestJson:{}):{}",domain,resourceDefine,operationDefine,requestBody,e);
            return  new ResponseEntity("{\"status\":1,\"failCode\":"+failCode+"}",HttpStatus.OK);
        }
    }
}
