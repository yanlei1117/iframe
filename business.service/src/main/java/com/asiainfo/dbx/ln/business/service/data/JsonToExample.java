package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExampleFactory;
import com.asiainfo.dbx.ln.component.util.AppBeanUtils;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.util.AppTypeConvertUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 String str1 =  "{\"name\":\"yanlei\",\"age\":\"30\",\"sex\":\"boy\",\"id\":\"1193\"}";
 String str2 =  "{\"name\":\"yanlei\",\"age\":{\"$ge\":\"25\",\"$le\":\"35\"},\"sex\":{\"$in\":[\"boy\",\"girl\"]},\"id\":{\"$between\":[\"1000\",\"1900\"]}}";
 String str3 =  "{\"name\":\"yanlei\",\"$or\":{\"age\":\"32\",\"age\":\"33\"}}";
 *
 *
 * Created by yanlei on 2014/8/27.
 */
public class JsonToExample {
    public static   MyBatisExample toExample(Map jsonMap,Class modelClass,MyBatisExample myBatisExample) throws Exception{
            MyBatisExample.Criteria criteria = myBatisExample.createCriteria(modelClass);
            generateMapExpression(jsonMap,jsonMap,myBatisExample,criteria,modelClass);
            return myBatisExample;
    }

    private static void generateMapExpression(Map jsonSrcMap,Map jsonMap, MyBatisExample myBatisExample,MyBatisExample.Criteria criteria ,Class modelClass) throws Exception{
        Iterator<String> iterator = jsonMap.keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Object value = jsonMap.get(name);
            if (name != null && name.trim().length() > 0) {
                generateExpresssion(jsonSrcMap,name.trim(), value, myBatisExample, criteria, modelClass);
            }
        }
    }
    private static void generateExpresssion(Map jsonSrcMap,String name,Object value,MyBatisExample example,MyBatisExample.Criteria criteria,Class modelClass) throws Exception{
        if(isOperator(name)){
            if(isOr(name) || isAnd(name)){
                if(!Map.class.isAssignableFrom(value.getClass())){
                    throw new Exception("fail at key named '"+name+"’,its value should be {.....}");
                }
                if(isOr(name)){
                    MyBatisExample.Criteria orCriteria =  example.createCriteria();
                    generateMapExpression(jsonSrcMap,(Map)value,example,orCriteria,modelClass);
                    if(orCriteria.isValid()){
                        criteria.orCriteria(orCriteria);
                    }
                }else if( isAnd(name)){
                    generateMapException(jsonSrcMap,name,(Map)value,example,criteria,modelClass);
                }
            }else{
                throw new Exception("fail at  key named '"+name+"’,it should be a operator,for example:{$or,$and},jsonSrcMap="+jsonSrcMap);
            }
        }else {
            if (value != null) {
                if (Collection.class.isAssignableFrom(value.getClass())) {
                    throw new Exception("fail at  key named '"+name+"',its value should not be a List,jsonSrcMap="+jsonSrcMap);
                } else if (Map.class.isAssignableFrom(value.getClass())) {
                    generateMapException(jsonSrcMap,name,(Map)value,example,criteria,modelClass);
                }else {
                    operatorExpression(jsonSrcMap,name,"$eq",value,criteria);
                }
            }
        }
    }
    private static void generateMapException(Map jsonSrcMap,String name,Map<String,Object> valueMap,MyBatisExample example,MyBatisExample.Criteria criteria,Class modelClass) throws Exception{
        Iterator<String> iterator = valueMap.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            Object value = valueMap.get(key);
            if(isOperator(key) ){
                if(isOr(key) || isAnd(key)){
                    generateExpresssion(jsonSrcMap,key,value,example,criteria,modelClass);
                }else {
                    operatorExpression(jsonSrcMap,name, key, value, criteria);
                }
            }else{
                throw new Exception("fail at '"+key+"' of '"+name+"',it should be a operator,for example:{$eq,$ge,$gt,$le,$lt,$in,$between,$like},json="+jsonSrcMap);

            }
        }
    }

    private static boolean isOperator(String name){
        return name.charAt(0)=='$';
    }
    private static boolean isOr(String name){
        return name.equals("$or");
    }
    private static boolean isAnd(String name){
        return name.equals("$and");
    }
    private static void operatorExpression(Map jsonSrcMap ,String name,String operator,Object value,MyBatisExample.Criteria criteria) throws Exception{
        if(operator.equals("$eq")){
            simpleExpression(jsonSrcMap,name,"=",value,criteria);
        }else if(operator.equals("$ge")){
            simpleExpression(jsonSrcMap,name,">=",value,criteria);
        }else if(operator.equals("$gt")){

            simpleExpression(jsonSrcMap,name,">",value,criteria);
        }else if(operator.equals("$le")){
            simpleExpression(jsonSrcMap,name,"<=",value,criteria);
        }else if(operator.equals("$lt")){
            simpleExpression(jsonSrcMap,name,"<",value,criteria);
        }else if(operator.equals("$like")){
            simpleExpression(jsonSrcMap,name,"like", value, criteria);
        }else if(operator.equals("$in")){
            in(jsonSrcMap,name, value, criteria);
        }else if(operator.equals("$between")){

            between(jsonSrcMap,name,value,criteria);
        }else{
            throw new Exception("fail at key named  "+operator+"   for property '"+name+"',it  should be one of  {$eq,$ge,$gt,$le,$lt,$in,$between,$like},json="+jsonSrcMap);
        }
    }


    public static void simpleExpression(Map jsonSrcMap,String propertyName,String operator,Object value,MyBatisExample.Criteria criteria)throws Exception{
        if(!AppValidationUtils.isCollection(value)){
            String fileName = AppTypeConvertUtils.convertPropertyToFieldName(propertyName);
            criteria.addCriterion(fileName+" "+operator+" ",value,propertyName);
        }else {
            throw new Exception("fail at  key named '"+propertyName+"’, its value    should be String ,not be "+value+",json="+jsonSrcMap);
        }

    }
    private static void in(Map jsonSrcMap,String propertyName, Object value,MyBatisExample.Criteria criteria) throws Exception{
        if(!List.class.isAssignableFrom(value.getClass())){
            throw new Exception("fail at $between  for  key named '"+propertyName+", its value   should be [\"a\",\"b\"],json="+jsonSrcMap);
        }
        String fileName = AppTypeConvertUtils.convertPropertyToFieldName(propertyName);
        criteria.addCriterion(fileName+" in ",value,propertyName);
    }

    private static void between(Map jsonSrcMap,String propertyName,Object value,MyBatisExample.Criteria criteria) throws  Exception{
        if(List.class.isAssignableFrom(value.getClass())){
            List list = (List)value;
            if(list.size() >= 2){
                String fileName = AppTypeConvertUtils.convertPropertyToFieldName(propertyName);
                criteria.addCriterion(fileName+" between ",list.get(0),list.get(1),propertyName);
            }else{
                throw new Exception("fail at $between for  key named '"+propertyName+"',  its value  should be [\"a\",\"b\"],json="+jsonSrcMap);
            }
        }else{
            throw new Exception("fail at $between  for key named '"+propertyName+"',  its value  should be [\"a\",\"b\"],json="+jsonSrcMap);
        }


    }
}
