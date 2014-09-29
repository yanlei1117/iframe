package com.asiainfo.dbx.ln.component.exception;

/**
 * Created by yanlei on 2014/8/15.
 */
public class PropertyNotConfigedException extends RuntimeException{
    public PropertyNotConfigedException(String propertyName){
        super("property named '"+propertyName+"' not configured");
    }
    public PropertyNotConfigedException(String propertyName,String message){
        super(message+": property named '"+propertyName+"' not configured,");
    }
}
