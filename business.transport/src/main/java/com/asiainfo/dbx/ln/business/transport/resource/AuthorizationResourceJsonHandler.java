package com.asiainfo.dbx.ln.business.transport.resource;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.transport.http.authorization.AuthorizationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanlei on 2014/9/25.
 */
public class AuthorizationResourceJsonHandler extends AutoInjectResourceJsonHandler implements  ResourceJsonHandler {
    AuthorizationValidator authorizationValidator = null;

    public AuthorizationValidator getAuthorizationValidator() {
        return authorizationValidator;
    }

    public void setAuthorizationValidator(AuthorizationValidator authorizationValidator) {
        this.authorizationValidator = authorizationValidator;
    }



    @Override
    public ResponseEntity dealRequest(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine, String requestBody,ServletWebRequest servletWebRequest) {

        if(getAuthorizationValidator() != null){
            boolean authFlag = getAuthorizationValidator().isAuthorization(domain,resourceDefine,operationDefine,requestBody,servletWebRequest);
            if(!authFlag){
                ResponseEntity   responseEntity = new ResponseEntity(HttpStatus.UNAUTHORIZED);
                return responseEntity;
            }
        }
        ResponseEntity responseEntity = this.getResourceJsonHandler().dealRequest(domain,resourceDefine,operationDefine,  requestBody, servletWebRequest);
        return responseEntity;
    }
}
