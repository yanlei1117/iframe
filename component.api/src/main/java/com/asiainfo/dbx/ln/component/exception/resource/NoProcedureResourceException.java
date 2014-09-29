package com.asiainfo.dbx.ln.component.exception.resource;

/**
 * Created by yanlei on 2014/9/22.
 */
public class NoProcedureResourceException extends  RuntimeException{
    public NoProcedureResourceException(String repository, String container, String procedureName) {
        super("have none procedure resource define :'"+repository+"/"+container+"/"+procedureName+"'");
    }
}
