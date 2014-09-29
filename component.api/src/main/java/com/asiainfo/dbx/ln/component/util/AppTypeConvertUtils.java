package com.asiainfo.dbx.ln.component.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/8/27.
 */
public class AppTypeConvertUtils extends ConvertUtils {
    private static  Map<String,Class> beanPropertyTypeMap = new ConcurrentHashMap<String,Class>();
    private static Map<Class,Class> sqlTypeConvert = new ConcurrentHashMap<Class, Class>();
    static{
        sqlTypeConvert.put(java.util.Date.class,java.sql.Date.class);
    }

    public static void initConverter(){
        ConvertUtils.deregister(Date.class);
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPatterns(new String []{"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","yyyyMMdd","yyyy-MM-dd"});
        ConvertUtils.register(dateConverter, Date.class);
        SqlTimestampConverter sqlTimestampConverter = new SqlTimestampConverter();
        sqlTimestampConverter.setPatterns(new String []{"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","yyyyMMdd","yyyy-MM-dd"});
        ConvertUtils.deregister(Timestamp.class);
        ConvertUtils.register(sqlTimestampConverter, Timestamp.class);

        SqlDateConverter sqlDateConverter = new SqlDateConverter();
        sqlDateConverter.setPatterns(new String []{"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","yyyyMMdd","yyyy-MM-dd"});
        ConvertUtils.deregister(java.sql.Date.class);
        ConvertUtils.register(sqlDateConverter, java.sql.Date.class);

       /* Converter listConverter = new Converter(){
            @Override
            public <T> T convert(Class<T> type, Object value) {
                List list = (List)value;
                return null;
            }
        };
        ConvertUtils.register(listConverter, java.util.List.class);*/
    }
    private static Object convert(Object value, Class<?> beanClazz,String propertyName,boolean propertyTypeConvert) {
        String key = beanClazz.getName()+"."+propertyName;
        Class<?> type = beanPropertyTypeMap.get(key);
        if(type == null){
            Field field = AppFieldUtils.getDeclaredField(beanClazz,propertyName,true);
            if(field != null) {
                type = field.getType();
                if (propertyTypeConvert && sqlTypeConvert.containsKey(type)) {
                    type = sqlTypeConvert.get(type);
                }
                beanPropertyTypeMap.put(key, type);
            }else{
                throw new RuntimeException("not exist property named '"+propertyName+"' in class '"+beanClazz.getName()+"'");
            }
        }

        return   convert(value,type );
    }
    public static Object convert(Object value, Class<?> targetType) {
        if(AppValidationUtils.isNull(value)){
            return null;
        }
        if(Collection.class.isAssignableFrom(value.getClass())){
            Collection collection = (Collection )value;
            Iterator iterator = collection.iterator();
            if(iterator.hasNext()){
                Collection newCollection = null;
                try {
                     newCollection = (Collection) AppBeanUtils.instanceBean(value.getClass());
                }catch(Exception e){
                    throw new RuntimeException("instance bean by class '"+value.getClass().getName()+"' error:",e);
                }
                    while (iterator.hasNext()) {
                        Object obj = iterator.next();
                        Object newObj = AppTypeConvertUtils.convert(obj, targetType);
                        newCollection.add(newObj);
                    }
                    return newCollection;

            }
            return value;
        }
        return ConvertUtils.convert(value,targetType);
    }
    private static Object convert(Object value, Class<?> beanClazz,String propertyName) {
        return AppTypeConvertUtils.convert(value,beanClazz,propertyName,false);
    }
    public static Object convertSqlType(Object value, Class<?> beanClazz,String propertyName) {
        if(AppValidationUtils.isNull(value)){
            return null;
        }
        return AppTypeConvertUtils.convert(value, beanClazz, propertyName, true);
    }

    public static String  convertPropertyToFieldName(String propertyName){
        if(propertyName == null){
            return null;
        }else{
            for(int i=0;i<propertyName.length();i++){
                if(Character.isUpperCase(propertyName.charAt(i))){
                    String fieldName = propertyName.substring(0,i)+"_"+Character.toLowerCase(propertyName.charAt(i))+(propertyName.length()>i+1?propertyName.substring(i+1):"");
                    return convertPropertyToFieldName(fieldName);
                }
            }
            return propertyName;
        }
    }

}
