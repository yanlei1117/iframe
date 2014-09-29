package com.asiainfo.dbx.ln.component.json;

import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/7/12.
 */
public interface JsonMapper {


    public static final String mapPrefix="{";
    public static final String mapSubfix="}";
    public static final String listPrefix="[";
    public static final String listSubfix="]";

    public String serialize(Object obj) throws Exception;


    public <T> T deSerialize(String json,Class<T> classz) throws Exception;

    public List deSerializeToList(String json) throws Exception;

    public Map deSerializeToMap(String json) throws Exception;


}
