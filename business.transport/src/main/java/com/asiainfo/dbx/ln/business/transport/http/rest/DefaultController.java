package com.asiainfo.dbx.ln.business.transport.http.rest;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.transport.resource.ResourceJsonHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanlei on 2014/9/25.
 */
public abstract  class DefaultController {
    ResourceJsonHandler resourceJsonHandler;

    public ResourceJsonHandler getResourceJsonHandler() {
        return resourceJsonHandler;
    }

    public void setResourceJsonHandler(ResourceJsonHandler resourceJsonHandler) {
        this.resourceJsonHandler = resourceJsonHandler;
    }
    public ResponseEntity  deal(String businessDomainName,ResourceDefine resourceDefine,OperationDefine operationDefine,String requestBody,ServletWebRequest servletWebRequest) {
        String str = "response:HTTP/1.1 200 OK\n" +
                "Cache-Control: private, max-age=0\n" +
                "Content-Type: text/xml; charset=utf-8\n" +
                "Server: Microsoft-IIS/7.5\n" +
                "X-AspNet-Version: 4.0.30319\n" +
                "X-Powered-By: ASP.NET\n" +
                "Date: Sun, 28 Sep 2014 02:41:47 GMT\n" +
                "Content-Length: 348\n" +
                "\n" +
                "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><SyncDataResponse xmlns=\"http://tempuri.org/\"><SyncDataResult>true</SyncDataResult></SyncDataResponse></soap:Body></soap:Envelope>";
        return this.getResourceJsonHandler().dealRequest(businessDomainName,resourceDefine,operationDefine,requestBody,servletWebRequest);
    }
}
