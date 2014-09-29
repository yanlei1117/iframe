package com.asiainfo.dbx.ln.component.exception;

/**
 * Created by yanlei on 2014/8/15.
 */
public class ExceptionFactory {
    public static  PropertyNotConfigedException createPropertyNotConfigedException(String propertyName){
        return new PropertyNotConfigedException(propertyName);
    }
    public static  PropertyNotConfigedException createPropertyNotConfigedException(String propertyName,String message){
        return new PropertyNotConfigedException(propertyName,message);
    }

}
