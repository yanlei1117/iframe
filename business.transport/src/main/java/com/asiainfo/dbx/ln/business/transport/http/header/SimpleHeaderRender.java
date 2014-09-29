package com.asiainfo.dbx.ln.business.transport.http.header;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/9/25.
 */
public class SimpleHeaderRender  implements  HeaderRender{
    Map<String,String> headerMap = new ConcurrentHashMap<String,String>();

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    String domainRegex;

    public String getDomainRegex() {
        return domainRegex;
    }

    public void setDomainRegex(String domainRegex) {
        this.domainRegex = domainRegex;
    }

    @Override
    public void render(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine, ResponseEntity responseEntity,ServletWebRequest servletWebRequest) {
        if(this.getDomainRegex() == null || AppStringUtils.matchFullString(this.getDomainRegex(),domain)){
            Iterator<String> iterator =  headerMap.keySet().iterator();
            while(iterator.hasNext()){
                String headerName = iterator.next();
                String headerValue = headerMap.get(headerName);
                responseEntity.getHeaders().add(headerName,headerValue);
            }
        }

    }
}
