package com.asiainfo.dbx.ln.component.exception.json;

import org.omg.CORBA.*;

import java.lang.Object;

/**
 * Created by yanlei on 2014/8/28.
 */
public class JsonDeSerializeFailException extends RuntimeException{
    public JsonDeSerializeFailException(String  jsonStr,Class targetPojoClass,Throwable e){
        super("json deserialize to "+targetPojoClass.getName()+" fail,jsonStr:"+jsonStr, e);
    }
    public JsonDeSerializeFailException(String  jsonStr,Class targetPojoClass){
        super("json deserialize to "+targetPojoClass.getName()+" fail,jsonStr:"+jsonStr);
    }
}
