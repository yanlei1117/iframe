package com.asiainfo.dbx.ln.business.transport.http.header;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by yanelei on 2014/9/25.
 */
public interface HeaderRender {
   public void render(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine,ResponseEntity responseEntity,ServletWebRequest servletWebRequest);
}
