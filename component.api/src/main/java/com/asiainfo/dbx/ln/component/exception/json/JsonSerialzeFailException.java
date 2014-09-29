package com.asiainfo.dbx.ln.component.exception.json;

/**
 * Created by yanlei on 2014/8/28.
 */
public class JsonSerialzeFailException extends RuntimeException{
    public JsonSerialzeFailException(Object obj ){
        super("json serialize fail,POJO:"+obj);
    }
    public JsonSerialzeFailException(Object obj ,Throwable e){
        super("json serialize fail,POJO:"+obj,e);
    }
}
