package com.asiainfo.dbx.ln.business.transport.resource;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

/**
 * Created by yanlei on 2014/9/25.
 */
public class DefaultAutoInjectResourceJsonHandler implements  ResourceJsonHandler{
    List<AutoInjectResourceJsonHandler> list ;

    public List<AutoInjectResourceJsonHandler> getList() {
        return list;
    }

    public void setList(List<AutoInjectResourceJsonHandler> list) {
        this.list = list;
        if(this.list != null){
            for(int i=this.list.size()-2;i>=0;i--){
                this.list.get(i).setResourceJsonHandler(this.list.get(i+1));
            }
        }
    }

    @Override
    public ResponseEntity dealRequest(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine, String requestBody, ServletWebRequest servletWebRequest) {
        return this.getList().get(0).dealRequest(domain,resourceDefine,operationDefine,requestBody,servletWebRequest);
    }
}
