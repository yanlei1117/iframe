package com.asiainfo.dbx.ln.component.json.impl;

import com.asiainfo.dbx.ln.component.json.JsonMapper;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 对不标准的json格式进行转换
 * 如 属性名上没有双引号，值上没有双引号
 * Created by Administrator on 2014/7/12.
 */
public class JsonMapperNoStandardJson implements JsonMapper{
    private final Logger logger = LoggerFactory.getLogger(JsonMapperNoStandardJson.class);
    private JsonMapper jsonMapper;

    public JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }



    @Override
    public List deSerializeToList(String json) throws Exception {
        String standJsonString = AppJsonUtils.formatToStandardJsonString(json);
        if(AppValidationUtils.notNull(standJsonString)){
            return this.getJsonMapper().deSerializeToList(standJsonString);
        }else{
            logger.error("convert json to standard error:{}",json);
            return null;
        }
    }

    @Override
    public Map deSerializeToMap(String json) throws Exception {
        String standJsonString = AppJsonUtils.formatToStandardJsonString(json);
        if(AppValidationUtils.notNull(standJsonString)){
            return this.getJsonMapper().deSerializeToMap(standJsonString);
        }else{
            logger.error("convert json to standard error:{}",json);
            return null;
        }
    }

    @Override
    public String serialize(Object obj) throws Exception {
        return this.getJsonMapper().serialize(obj);
    }

    @Override
    public <T> T deSerialize(String json, Class<T> classz) throws Exception {
        String standJsonString = AppJsonUtils.formatToStandardJsonString(json);
        if(AppValidationUtils.notNull(standJsonString)){
            return this.getJsonMapper().deSerialize(standJsonString,classz);
        }else{
            logger.error("convert json to standard error:{}",json);
            return null;
        }
    }
}
