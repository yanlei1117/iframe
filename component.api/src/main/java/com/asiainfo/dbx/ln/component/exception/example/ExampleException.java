package com.asiainfo.dbx.ln.component.exception.example;

/**
 * Created by yanlei on 2014/8/28.
 */
public class ExampleException {
    public static QueryByExampleStringException createQueryByExampleStringException(String queryByExampleString,Throwable e){
        return new QueryByExampleStringException(queryByExampleString,e);
    }
}
