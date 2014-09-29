package com.asiainfo.dbx.ln.business.transport.http.header;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.business.service.operation.OperationDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.*;

/**
 * Created by yanlei on 2014/9/25.
 */
public class DefaultHeaderRender implements  HeaderRender ,Loader {
    private Collection<HeaderRender> headerRenderCollection = null;

    public Collection<HeaderRender> getHeaderRenderCollection() {
        return headerRenderCollection;
    }

    public void setHeaderRenderCollection(Collection<HeaderRender> headerRenderCollection) {
        this.headerRenderCollection = headerRenderCollection;
    }


    @Override
    public void load() throws Exception {
        Map<String,HeaderRender> map =  AppSpringUtils.getApplicationContextHolder().getBeansOfType(HeaderRender.class);
        if(map != null && map.size()>0){
            headerRenderCollection = new ArrayList(map.size());
            for(HeaderRender headerRender:map.values()){
                if(headerRender != this){
                    headerRenderCollection.add(headerRender);
                }
            }
        }else{
            headerRenderCollection = Collections.EMPTY_LIST;
        }
    }



    @Override
    public void render(String domain, ResourceDefine resourceDefine, OperationDefine operationDefine,ResponseEntity responseEntity ,ServletWebRequest servletWebRequest) {
        if(this.getHeaderRenderCollection() != null){
            for(HeaderRender headerRender:this.getHeaderRenderCollection()){
                headerRender.render(domain,resourceDefine,operationDefine,responseEntity,servletWebRequest);
            }
        }
    }
}
