package com.asiainfo.dbx.ln.component.json.impl;

import com.asiainfo.dbx.ln.component.exception.json.JsonExceptionFactory;
import com.asiainfo.dbx.ln.component.json.JsonMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Json字符串与java 对象的转换
 * Created by Administrator on 2014/7/12.
 */
public class JsonMapperJackson implements JsonMapper {
    private final ObjectMapper objectMapper ;
    public JsonMapperJackson(){
        objectMapper = new ObjectMapper();
        //允许单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 支持无引号属性名
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        objectMapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        // 空值处理为空串
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
        {

            @Override
            public void serialize(
                    Object value,
                    JsonGenerator jg,
                    SerializerProvider sp) throws IOException, JsonProcessingException
            {
                jg.writeString("");
            }
        });
    }
    private ObjectMapper getObjectMapper() {

        return objectMapper;
    }

    @Override
    public String serialize(Object obj) throws Exception{
        // TODO Auto-generated method stub
        try {
            return this.getObjectMapper().writeValueAsString(obj);
        }catch (Throwable e){
            throw JsonExceptionFactory.createJsonSerialzeFailException(obj,e);
        }
    }

    @Override
    public List deSerializeToList(String json) throws Exception {
        try{
                return deSerialize(json,List.class);
        }catch (Throwable e){
            throw JsonExceptionFactory.createJsonDeSerializeFailException(json,List.class,e);
        }
    }

    @Override
    public Map deSerializeToMap(String json) throws Exception {
        try{
             return deSerialize(json,Map.class);
        }catch (Throwable e){
            throw JsonExceptionFactory.createJsonDeSerializeFailException(json,Map.class,e);
        }
    }

    @Override
    public <T> T deSerialize(String json, Class<T> classz) throws Exception{
        // TODO Auto-generated method stub
        try{
            return this.getObjectMapper().readValue(json, classz);
        }catch (Throwable e){
            throw JsonExceptionFactory.createJsonDeSerializeFailException(json,classz,e);
        }


    }

}
