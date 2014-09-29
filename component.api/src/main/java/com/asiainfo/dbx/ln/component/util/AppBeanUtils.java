package com.asiainfo.dbx.ln.component.util;




import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * Created by yanlei on 2014/7/17.
 */
public class AppBeanUtils extends BeanUtils {
   private static  LocalVariableTableParameterNameDiscoverer  localVariableTableParameterNameDiscoverer  = new LocalVariableTableParameterNameDiscoverer();

  private static   PropertyUtilsBean propertyUtilsBean  =   new PropertyUtilsBean();
    /**
     * 获取方法的参数名
     * @param method
     * @return
     */
    public static String [] getMethodParameterNames(Method method){
        return localVariableTableParameterNameDiscoverer.getParameterNames(method);
    }

    public static Object instanceBean(String className) throws Exception{
       Class classz = AppClassUtils.instanceClass(className);
        return instanceBean(classz);
    }
    public static Object instanceBean(Class classz) throws Exception{
        return classz.newInstance();
    }

    public static Object instanceBean(String className,Map<String,Object> propertyMap) throws Exception{
        Object obj = instanceBean(className);
        AppBeanUtils.populate(obj,propertyMap);
        return obj;
    }

    public static Object getPropertyValue(Object obj,String propertyName) throws Exception{
        return propertyUtilsBean.getProperty(obj,propertyName);
    }

}
