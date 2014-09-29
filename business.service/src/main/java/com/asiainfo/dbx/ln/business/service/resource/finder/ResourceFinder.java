package com.asiainfo.dbx.ln.business.service.resource.finder;

import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;


/**
 * Created by yanlei on 2014/8/27.
 */
public interface ResourceFinder<E> {

    public E findResource(ResourceDefine resourceDefine);
}
