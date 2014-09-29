package com.asiainfo.dbx.ln.component.util;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/8/26.
 */
public class AppClassUtils extends ClassUtils {

    public static Class instanceClass(String className) throws Exception{
        if(AppClassLoaderUtils.getCurrentClassLoader()!= null){
            return   AppClassLoaderUtils.getCurrentClassLoader().loadClass(className);
        }else {
            return  AppBeanUtils.class.getClassLoader().loadClass(className);
        }
    }



    public static Class[] getInterfaceGenericTypes(Class classz ,Class interfaces){
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genTypes [] = classz.getGenericInterfaces();
        if(genTypes != null && genTypes.length >0){
            for(int i=0;i<genTypes.length;i++){
                if(genTypes[i] instanceof ParameterizedType){
                    if(((ParameterizedType)genTypes[i]).getRawType().equals(interfaces)){
                        return getGenerateType(genTypes[i]);
                    }
                }
            }
        }

        return   null ;
    }
    public static Class getInterfaceGenericTypes(Class classz ,Class interfaces,int index){
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Class classes [] =  AppClassUtils.getInterfaceGenericTypes(classz,interfaces);
        if(classes != null && classes.length>index){
            return classes[index];
        }
        return null;

    }
    private static Class [] getGenerateType(Type genType){
        Class classes [] = null;
        if ((genType instanceof ParameterizedType)) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if(params != null && params.length>0) {
                classes =new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    classes[i] = (Class) params[i];
                }
            }
        }
        return   classes ;
    }
    public static Class[] getSuperGenericTypes(Class classz ){
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = classz.getGenericSuperclass();

        return   getGenerateType(genType) ;
    }
    public static Class  getSuperGenericTypes(Class classz ,int index){
        Class classes [] =  AppClassUtils.getSuperGenericTypes(classz);
        if(classes != null && classes.length>index){
            return classes[index];
        }
        return null;
    }

    /**
     * 类名的上一层包名
     * @param classz
     * @return
     */
    public static  String getLastPackageName(Class classz){
        String className = classz.getName();
        int index = className.lastIndexOf(".");
        String packageName = className.substring(0,index);
        index = packageName.lastIndexOf(".");
        packageName  =  packageName.substring(index+1);
        return  packageName;
    }

}
