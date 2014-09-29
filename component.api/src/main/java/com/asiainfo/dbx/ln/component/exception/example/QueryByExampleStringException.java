package com.asiainfo.dbx.ln.component.exception.example;

/**
 * Created by yanlei on 2014/8/28.
 */
public class QueryByExampleStringException extends  RuntimeException{
    public QueryByExampleStringException(String queryString,Throwable e){
        super("das query by example string error:"+queryString,e);
    }
}
