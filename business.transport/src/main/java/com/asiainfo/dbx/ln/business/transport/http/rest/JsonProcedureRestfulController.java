package com.asiainfo.dbx.ln.business.transport.http.rest;


import com.asiainfo.dbx.ln.component.exception.resource.NoProcedureResourceException;
import com.asiainfo.dbx.ln.business.service.ServiceSupport;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
public class JsonProcedureRestfulController extends  DefaultController{
    private final Logger logger = LoggerFactory.getLogger(JsonProcedureRestfulController.class);


    public HttpEntity<String> deal(OperationDefine operationDefine, String businessDomainName, String repository,String container ,String procedureName,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest) {
        try {
            ProcedureResourceDefine procedureResourceDefine = ResourceDefineFactory.getProcedureResourceDefine(repository, container, procedureName);
            return deal(businessDomainName,procedureResourceDefine,operationDefine, httpEntity.getBody(), servletWebRequest);
        }catch(NoProcedureResourceException e){
            logger.error("",e);
            ResponseEntity   responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            return responseEntity;
        }
    }

    @RequestMapping(value={"/{businessDomainName}/procedure/{repository}/{container}/{procedureName}"},method =  RequestMethod.GET ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity read(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("procedureName") String procedureName ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest) {
            return deal(OperationDefineFactory.getOperationDefineForRead(),businessDomainName,repository,container,procedureName,httpEntity,servletWebRequest);
    }



    @RequestMapping(value={"/{businessDomainName}/procedure/{repository}/{container}/{procedureName}"},method =  RequestMethod.POST ,consumes={MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity create(@PathVariable("businessDomainName") String businessDomainName,@PathVariable("repository") String repository,@PathVariable("container") String container ,@PathVariable("procedureName") String procedureName ,HttpEntity<String> httpEntity,ServletWebRequest servletWebRequest){
        return deal(OperationDefineFactory.getOperationDefineForRead(),businessDomainName,repository,container,procedureName,httpEntity,servletWebRequest);
    }






}
