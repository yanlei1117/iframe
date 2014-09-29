package com.asiainfo.dbx.ln.component.util;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;


public class AppStringUtils extends StringUtils {
    private final static ConcurrentHashMap<String,Pattern> patternMap = new ConcurrentHashMap<String, Pattern>();
    public final  static boolean matchFullString(String regex,String target){
        return createPattern(regex).matcher(target).matches();
    }
    public final static boolean matchPartString(String regex,String target){
        return createPattern(regex).matcher(target).find();
    }
    private final static  Pattern createPattern(String regex){
        Pattern pattern =  patternMap.get(regex);
        if(AppValidationUtils.isNull(pattern)){
            pattern =  Pattern.compile(regex);
            patternMap.putIfAbsent(regex,pattern);
        }

        return pattern;
    }
    public final static String  getUUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    }
    public final static  String firstCharToUpperCase(String str ){
        if(str == null ){
            return null;
        }
        if(str.length()>0){
            if(Character.isUpperCase(str.charAt(0))){
                return str;
            }else{
                if(str.length()>1) {
                    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
                }else{
                    return Character.toUpperCase(str.charAt(0))+"";
                }
            }
        }else{
            return str;
        }
    }

    /**
     * restful 中资源名往往是复数如：persons,books
     * 将其转换为对应的类名
     * @param str
     * @return
     */
    public final static String resourceNameToClassName(String str){
        if(str == null ){
            return null;
        } if(str.length()>1){
            if(str.charAt(str.length()-1)=='s'){//复数 persions
                str = str.substring(0,str.length()-1);
            }
            if(Character.isUpperCase(str.charAt(0))){
                return str;
            }else{
               return Character.toUpperCase(str.charAt(0)) + str.substring(1);
            }
        }else{
            return str;
        }
    }
    /**
     * 判断是否为变量
     * @param name
     * @return
     * @throws Exception
     */
    public final static boolean isVar(String name,String varPrefix,String varSubfix) throws Exception{
        if(name == null){
            return false;
        }else{
            name = name.trim();
        }
        if(name.startsWith(varPrefix) && name.endsWith(varSubfix)){
            return true;
        }else{
            return false;
        }
    }

    public final static List<String> getVarNames(String strExpression,String varPrefix,String varSubfix) throws Exception{
        List<String> list = null;
        int index = strExpression.indexOf(varPrefix);
        if(index>=0){
            list = new LinkedList<String>();
        }
        int movePostion = index+1;
        int subfixPostion = index+1;
        while(index >=0){
            int endIndex = strExpression.indexOf(varSubfix,subfixPostion);
            if(endIndex>0){
                int tempIndex = strExpression.indexOf(varPrefix,movePostion);
                if(tempIndex != -1 && tempIndex<endIndex){
                    movePostion=tempIndex +1;
                    subfixPostion=endIndex+1;
                    continue;
                }
                String var = strExpression.substring(index,endIndex+1);
                list.add(var);
            }else{
                throw new Exception("String:"+strExpression+" define error");
            }
            if(endIndex == strExpression.length()-1){
                break;
            }

            index = strExpression.indexOf(varPrefix,endIndex);
            movePostion = index+1;
            subfixPostion = index+1;
        }
        return list;
    }
    public final  static String getVarOne(String strExpression,String varPrefix,String varSubfix ) throws Exception{
        if(strExpression == null){
            return null;
        }else{
            strExpression = strExpression.trim();
        }
        if(strExpression.startsWith(varPrefix) && strExpression.endsWith(varSubfix)){
            return strExpression.substring(2,strExpression.length()-1);
        }else{
            return strExpression;
        }
    }

    private static AtomicLong seqLong = new AtomicLong(1000);
    public static long getSequenceCode(){
        if(seqLong.longValue() >Long.MAX_VALUE-10000){
            synchronized (AppStringUtils.class){
                if(seqLong.longValue() >Long.MAX_VALUE-10000) {
                    seqLong = new AtomicLong(1000);
                }
            }
        }
        return seqLong.incrementAndGet();
    }
}
