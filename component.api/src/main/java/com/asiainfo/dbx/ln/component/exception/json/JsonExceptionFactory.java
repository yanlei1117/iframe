package com.asiainfo.dbx.ln.component.exception.json;

/**
 * Created by yanlei on 2014/8/28.
 */
public class JsonExceptionFactory {
    public static JsonDeSerializeFailException createJsonDeSerializeFailException(String jsonStr,Class targetPojoClass){
        return new JsonDeSerializeFailException(jsonStr,targetPojoClass);
    }
    public static JsonDeSerializeFailException createJsonDeSerializeFailException(String jsonStr,Class targetPojoClass,Throwable e){
        return new JsonDeSerializeFailException(jsonStr,targetPojoClass,e);
    }
    public static JsonSerialzeFailException createJsonSerialzeFailException(Object pojo){
        return new JsonSerialzeFailException(pojo);
    }
    public static JsonSerialzeFailException createJsonSerialzeFailException(Object pojo,Throwable e){
        return new JsonSerialzeFailException(pojo,e);
    }
}
