package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by yanlei on 2014/7/12.
 */
public class AppJsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(AppJsonUtils.class);

    private volatile  static JsonMapper jsonMapper;


    private static JsonMapper getJsonMapper() throws Exception{
        if(jsonMapper == null){
            synchronized (AppJsonUtils.class){
                JsonMapper jsonMapper1 =  (JsonMapper)AppSpringUtils.getApplicationContextHolder().getBean("jsonMapper");
                if(jsonMapper1 ==null){
                    throw new Exception("no bean named 'jsonMapper'");
                }
                jsonMapper = jsonMapper1;
            }
        }
        return jsonMapper;
    }

    public static String objToJson(Object obj) throws Exception {
        if (AppValidationUtils.notNull(obj)) {
            return AppJsonUtils.getJsonMapper().serialize(obj);
        } else {
            return null;
        }
    }

    public static <T> T jsonToObj(String jsonString, Class<T> classz) throws Exception {
        if (AppValidationUtils.notEmpty(jsonString)) {
            return AppJsonUtils.getJsonMapper().deSerialize(jsonString, classz);
        } else {
            return null;
        }


    }

    public static List jsonToList(String jsonString) throws Exception {
        if (AppValidationUtils.notEmpty(jsonString)) {
            return AppJsonUtils.getJsonMapper().deSerializeToList(jsonString);
        } else {
            return null;
        }


    }

    public static Map jsonToMap(String jsonString) throws Exception {
        if (AppValidationUtils.notEmpty(jsonString)) {
            return AppJsonUtils.getJsonMapper().deSerializeToMap(jsonString);
        } else {
            return null;
        }
    }

    public static Map jsonToMap(String jsonString,boolean countExpression) throws Exception {
        if(jsonString == null){
            return null;
        }
        Map map = jsonToMap(jsonString);
        if(map == null ||map.size() ==0 || !countExpression){
            return map;
        }else {
            return (Map)convertValue(map);
        }
    }

    private  static Object convertValue(Object value) throws Exception{
        if(value instanceof  String ){
            return AppVarUtils.getVarContainer(AppJsonUtils.class).getVar((String)value);
        }else   if(Map.class.isAssignableFrom(value.getClass())){
            Map map = (Map)value;
            Map newMap = new LinkedHashMap();
            Iterator iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                Object key = iterator.next();
                if(key instanceof  String){
                    key = AppVarUtils.getVarContainer(AppJsonUtils.class).getVar((String)key);
                }
                Object value1 =map.get(key);
                if(value1 != null) {
                    value1 = convertValue(value1);
                }
                newMap.put(key,value1);
            }
            return newMap;
        }else if(List.class.isAssignableFrom(value.getClass())){
            List list = (List)value;
            List newList = new ArrayList();
            for(Object obj :list){
                if(obj != null){
                    obj = convertValue(obj);
                }
                newList.add(obj);
            }
            return newList;
        }else if(Set.class.isAssignableFrom(value.getClass())){
            Set set = (Set)value;
            Iterator iterator = set.iterator();
            Set newSet = new HashSet();
            while(iterator.hasNext()){
                Object obj =  iterator.next();
                if(obj != null){
                      obj = convertValue(obj);
                }
                newSet.add(obj);
            }
            return newSet;
        }else{
            return value;
        }
    }
    /**
     * *  试图转换json字符串为List或Map ,转换失败则返回原json字符串
     *
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static Object jsonToObj(String jsonString) throws Exception {
        if (AppValidationUtils.notEmpty(jsonString)) {

            jsonString = jsonString.trim();
            if (jsonString.startsWith(JsonMapper.mapPrefix) && jsonString.endsWith(JsonMapper.mapSubfix)) {
                return jsonToObj(jsonString, Map.class);
            } else if (jsonString.startsWith(JsonMapper.listPrefix) && jsonString.endsWith(JsonMapper.listSubfix)) {
                return jsonToObj(jsonString, List.class);
            } else {
                return jsonString;
            }
        } else {
            return null;
        }
    }



    public static String formatToStandardJsonString(String jsonString) {
        if (jsonString == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            jsonString = jsonString.trim();
            if (jsonString.startsWith(JsonMapper.mapPrefix) && jsonString.endsWith(JsonMapper.mapSubfix)) {

                jsonString = jsonString.substring(1, jsonString.length() - 1);
                if( formatJsonMap(jsonString,sb)){
                    return sb.toString();
                }
            } else if (jsonString.startsWith(JsonMapper.listPrefix) && jsonString.endsWith(JsonMapper.listSubfix)) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
                if(formatJsonList(jsonString,sb)){
                    return sb.toString();
                }
            }
            return null;
        }

    }

    private static boolean fromatJson(String jsonString, StringBuilder sb) {
        if (jsonString == null) {
            return false;
        } else {

            jsonString = jsonString.trim();
            if (jsonString.startsWith(JsonMapper.mapPrefix) && jsonString.endsWith(JsonMapper.mapSubfix)) {

                jsonString = jsonString.substring(1, jsonString.length() - 1);
                return formatJsonMap(jsonString, sb);
            } else if (jsonString.startsWith(JsonMapper.listPrefix) && jsonString.endsWith(JsonMapper.listSubfix)) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
                return formatJsonList(jsonString, sb);
            } else {
               return  formatJsonPropertyNameOrValue(jsonString,sb);
            }

        }

    }




    private static  boolean formatJsonPropertyNameOrValue(String jsonString,StringBuilder sb){
           char firstChar = jsonString.charAt(0);
           char lastChar = jsonString.charAt(jsonString.length()-1);
           if(firstChar=='\'' && firstChar=='\''){
               sb.append("\"");
               sb.append(jsonString.substring(1,jsonString.length()-1));
               sb.append("\"");

           }else if(firstChar=='\"' && firstChar=='\"'){
                sb.append(jsonString);
           }else if(jsonString.equalsIgnoreCase("true") || jsonString.equalsIgnoreCase("false")){

               sb.append(jsonString);
           }else if(AppNumberUtils.isNumber(jsonString)){
               sb.append(jsonString);

           }else if( jsonString.equalsIgnoreCase("null")){
               sb.append(jsonString);

           }else{
               sb.append("\"");
               sb.append(jsonString);
               sb.append("\"");

           }
        return true;
    }

    private static boolean formatJsonList(String str,StringBuilder sb){
        str = str.trim();
        sb.append(JsonMapper.listPrefix);
        int jsonLength = str.length();
        int sbLength =sb.length();

        int mapValue =0;
        int arrayValue = 0;
        boolean  duotFlag=false;
        int startIndex =0;
        for(int i=0;i<str.length();i++){
            char a = str.charAt(i);
            switch(a){
                case '{':if(!duotFlag)mapValue++;break;
                case '}':if(!duotFlag)mapValue--;break;
                case '[':if(!duotFlag)arrayValue++;break;
                case ']':if(!duotFlag)arrayValue--;break;
                case '\"':if(str.charAt(i-1)!='\\')duotFlag=!duotFlag;break;
                case ',':
                    if(mapValue==0 && arrayValue==0&& !duotFlag){
                        String value = str.substring(startIndex,i);

                        if(!fromatJson(value, sb)) {
                            return false;
                        }
                        sb.append(",");
                        startIndex = i+1;
                    }
                    break;
            }
        }
        if(mapValue==0 && arrayValue==0&& !duotFlag){
            String value = str.substring(startIndex);

            if(!fromatJson(value, sb)) {
                return false;
            }
            if(jsonLength>0 && sb.length() == sbLength) {
                return false;
            }


        }else {
            return false;
        }
        sb.append(JsonMapper.listSubfix);
        return true;
    }

    private static boolean formatJsonMap(String str,StringBuilder sb){
        str = str.trim();
        sb.append(JsonMapper.mapPrefix);
        int jsonLength = str.length();
        int sbLength =sb.length();

        int mapValue =0;
        int arrayValue = 0;
        boolean  duotFlag=false;
        int startIndex =0;
        String name = null;
        for(int i=0;i<str.length();i++){
            char a = str.charAt(i);
            switch(a){

                case '{':if(!duotFlag)mapValue++;break;
                case '}':if(!duotFlag)mapValue--;break;
                case '[':if(!duotFlag)arrayValue++;break;
                case ']':if(!duotFlag)arrayValue--;break;
                case '\"':if(str.charAt(i-1)!='\\')duotFlag=!duotFlag;break;

                case ',':
                    if(mapValue==0 && arrayValue==0&& !duotFlag && name != null){
                        String value = str.substring(startIndex,i);
                        if(!fromatJson(value, sb)) {

                            return false;
                        }
                        sb.append(",");
                        startIndex = i+1;
                        name = null;
                    }
                    break;
                case ':':
                    if(mapValue==0 && arrayValue==0&& !duotFlag&& name == null){
                        name=str.substring(startIndex,i);
                        if(fromatJson(name, sb)) {
                            sb.append(":");

                        }else{
                            return false;
                        }
                        startIndex = i+1;
                    }
                    break;

            }
        }
        if(mapValue==0 && arrayValue==0&& !duotFlag && name != null){
            String value = str.substring(startIndex);
            if(!fromatJson(value, sb)) {
                return false;
            }
            if(jsonLength>0 && sb.length() == sbLength) {
                return false;
            }
        }else {
            return false;
        }
        sb.append(JsonMapper.mapSubfix);
        return true;
    }

}
