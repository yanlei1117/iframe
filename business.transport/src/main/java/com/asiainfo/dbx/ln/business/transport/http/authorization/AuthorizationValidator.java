package com.asiainfo.dbx.ln.business.transport.http.authorization;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanlei on 2014/9/25.
 */
public interface AuthorizationValidator {
    public boolean isAuthorization(String domain,ResourceDefine resourceDefine,OperationDefine operationDefine,String requestBody,ServletWebRequest servletWebRequest);
}
