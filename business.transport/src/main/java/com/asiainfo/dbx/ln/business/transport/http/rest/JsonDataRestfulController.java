package com.asiainfo.dbx.ln.business.transport.http.rest;


import com.asiainfo.dbx.ln.component.exception.resource.NoDataResourceException;
import com.asiainfo.dbx.ln.business.service.*;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.DataResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;


import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanlei on 2014/9/1.
 */
@Controller
@SuppressWarnings("unchecked")
public class JsonDataRestfulController extends  DefaultController{
    private final Logger logger = LoggerFactory.getLogger(JsonDataRestfulController.class);


    public HttpEntity<String> deal(OperationDefine operationDefine, String businessDomainName, String repository,String container ,String collection,String itemId,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest) {
        try {
            DataResourceDefine dataResourceDefine = ResourceDefineFactory.getDataResourceDefine(repository, container, collection);
            dataResourceDefine.setItemId(itemId);
            return super.deal(businessDomainName,dataResourceDefine,operationDefine,httpEntity==null?null: httpEntity.getBody(), servletWebRequest);
        }catch(NoDataResourceException e){
            logger.error("",e);
            ResponseEntity   responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            return responseEntity;
        }

    }
    public HttpEntity<String> deal(OperationDefine operationDefine, String businessDomainName, String repository,String container ,String collection,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
            return deal(operationDefine,businessDomainName,repository,container,collection,null,httpEntity,servletWebRequest);
    }
    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}"},method =  RequestMethod.GET ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity read(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest) {
            return deal(OperationDefineFactory.getOperationDefineForRead(),businessDomainName,repository,container,collection,httpEntity,servletWebRequest);
    }

    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}/{itemId}"},method =  RequestMethod.GET ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity readById(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,@PathVariable("itemId") String itemId ,ServletWebRequest servletWebRequest) {
        return deal(OperationDefineFactory.getOperationDefineForRead(),businessDomainName,repository,container,collection,itemId,null,servletWebRequest);
    }


    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}"},method =  RequestMethod.POST ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity create(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
        return deal(OperationDefineFactory.getOperationDefineForCreate(),businessDomainName,repository,container,collection,httpEntity,servletWebRequest);
    }

    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}"},method =  RequestMethod.PUT ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity update(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
        return deal(OperationDefineFactory.getOperationDefineForUpdate(),businessDomainName,repository,container,collection,httpEntity,servletWebRequest);
    }

    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}/{itemId}"},method =  RequestMethod.PUT ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity updateById(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,@PathVariable("itemId") String itemId ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
        return deal(OperationDefineFactory.getOperationDefineForUpdate(),businessDomainName,repository,container,collection,itemId,httpEntity,servletWebRequest);
    }



    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}"},method =  RequestMethod.DELETE ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity delete(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
        return deal(OperationDefineFactory.getOperationDefineForDelete(),businessDomainName,repository,container,collection,httpEntity,servletWebRequest);
    }

    @RequestMapping(value={"/{businessDomainName}/data/{repository}/{container}/{collection}/{itemId}"},method =  RequestMethod.DELETE ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity deleteById(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("collection") String collection ,@PathVariable("itemId") String itemId,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
        return deal(OperationDefineFactory.getOperationDefineForDelete(),businessDomainName,repository,container,collection,itemId,httpEntity,servletWebRequest);
    }


}
