package com.asiainfo.dbx.ln.component.util;

import java.lang.reflect.Array;
import java.util.*;

public class AppValidationUtils {
    public static boolean notEmpty(String str ){
        if(str != null && str.trim().length()>0){
            return true;
        }else{
            return false;
        }
    }
	public static boolean notEmpty(Object obj ){
		if(obj == null){
			return false;
		}
		if(obj instanceof String){
			if(((String)obj).trim().length() ==0){
				return false;
			}
		}
		if(obj.getClass().isArray()){
			if(Array.getLength(obj)==0){
				return false;
			}
		}
		if(Collection.class.isAssignableFrom(obj.getClass())){
			if(((Collection)obj).size() ==0){
				return false;
			}
		}
		if(Map.class.isAssignableFrom(obj.getClass())){
			if(((Map)obj).size() ==0){
				return false;
			}
		}
		return true;
	}
	public  static boolean notNull(Object obj){
		return obj != null;
	}
    public static boolean isNull(Object obj){
        return obj == null;
    }
    public static boolean isEmpty(String obj){
        if(obj == null || obj.trim().length()==0){
            return true;
        }else {
            return false;
        }
    }
    public static boolean isEmpty(Object obj){
        if(obj == null){
            return true;
        }
        if(obj instanceof String){
            if(((String)obj).trim().length() ==0){
                return true;
            }
        }
        if(obj.getClass().isArray()){
            if(Array.getLength(obj)==0){
                return true;
            }
        }
        if(Collection.class.isAssignableFrom(obj.getClass())){
            if(((Collection)obj).size() ==0){
                return true;
            }
        }
        if(Map.class.isAssignableFrom(obj.getClass())){
            if(((Map)obj).size() ==0){
                return true;
            }
        }
        return false;
    }
    public static boolean isJsonString(String jsonString){
        return AppJsonUtils.formatToStandardJsonString(jsonString) != null;
    }
    public static  boolean isCollection(Object value){
        if(value == null){
            return false;
        }
        if(Collection.class.isAssignableFrom(value.getClass())){
            return true;
        }
        if(Map.class.isAssignableFrom(value.getClass())){
            return true;
        }

        if(value.getClass().isArray()){
            return true;
        }
        return false;
    }
}
