package com.asiainfo.dbx.ln.component.exception.resource;

/**
 * Created by yanlei on 2014/9/22.
 */
public class InvalidDataResourceException extends  RuntimeException{
    public InvalidDataResourceException(String repository, String container, String collection) {
        super("invalid data resource define : '"+repository+"/"+container+"/"+collection+"'");
    }
}
