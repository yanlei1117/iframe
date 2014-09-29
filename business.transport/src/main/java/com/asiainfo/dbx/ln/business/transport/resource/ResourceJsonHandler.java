package com.asiainfo.dbx.ln.business.transport.resource;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanlei on 2014/9/25.
 */
public interface ResourceJsonHandler {
    public ResponseEntity dealRequest(String domain,ResourceDefine resourceDefine,OperationDefine operationDefine,String requestBody,ServletWebRequest servletWebRequest) ;
}
