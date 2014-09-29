package com.asiainfo.dbx.ln.business.transport.domain;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;

import org.springframework.http.HttpEntity;

/**
 * Created by yanlei on 2014/9/2.
 */
public interface BusinessDomain {
    public boolean matchDomainName(String domaiName) ;
    public void renderResponseHeader(HttpEntity responseHttpEntity)throws Exception;
    public OperationDefine findOperationDefine( ) throws Exception;
    public void initRequest() throws Exception;
    public boolean isAuthorizationForResourceByLogin(ResourceDefine resourceDefine) throws Exception;
}
