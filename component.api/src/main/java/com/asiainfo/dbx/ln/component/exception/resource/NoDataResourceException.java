package com.asiainfo.dbx.ln.component.exception.resource;

/**
 * Created by yanlei on 2014/9/22.
 */
public class NoDataResourceException extends  RuntimeException{
    public NoDataResourceException(String repository, String container, String collection) {
        super("have none data resource define :'"+repository+"/"+container+"/"+collection+"'");
    }
}
