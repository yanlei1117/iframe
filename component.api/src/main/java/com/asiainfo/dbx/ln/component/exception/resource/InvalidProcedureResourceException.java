package com.asiainfo.dbx.ln.component.exception.resource;

/**
 * Created by yanlei on 2014/9/22.
 */
public class InvalidProcedureResourceException extends  RuntimeException{
    public InvalidProcedureResourceException(String repository, String container, String procedureName) {
        super("invalid procedure resource define :'"+repository+"/"+container+"/"+procedureName+"'");
    }
}
