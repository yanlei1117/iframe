package com.asiainfo.dbx.ln.business.exception.Service;

import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;


/**
 * Created by yanlei on 2014/9/1.
 */
public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException (ResourceDefine resourceDefine,Exception e) {
        super("resource not found :"+ resourceDefine.toString(),e);
    }
}
