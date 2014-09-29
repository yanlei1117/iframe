package com.asiainfo.dbx.ln.business.transport.resource;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.transport.http.header.HeaderRender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanlei on 2014/9/25.
 */
public class HeaderRenderResourceHandler extends AutoInjectResourceJsonHandler implements  ResourceJsonHandler {

    HeaderRender headerRender = null;

    public HeaderRender getHeaderRender() {
        return headerRender;
    }

    public void setHeaderRender(HeaderRender headerRender) {
        this.headerRender = headerRender;
    }

    @Override
    public ResponseEntity dealRequest(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine, String requestBody,ServletWebRequest servletWebRequest) {
        ResponseEntity responseEntity = this.getResourceJsonHandler().dealRequest(domain,resourceDefine,operationDefine,  requestBody, servletWebRequest);
        if(this.getHeaderRender() != null){
            this.getHeaderRender().render(domain,resourceDefine,operationDefine,responseEntity,servletWebRequest);
        }
        return responseEntity;
    }
}
