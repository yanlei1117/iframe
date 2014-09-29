package com.asiainfo.dbx.ln.component.exception.resource;

/**
 * Created by yanlei on 2014/9/25.
 */
public class DuplicateProcedureResourceException extends RuntimeException{
    public DuplicateProcedureResourceException(String repository, String container, String procedureName) {
        super("duplicate procedure resource define :'"+repository+"/"+container+"/"+procedureName+"'");
    }
}
