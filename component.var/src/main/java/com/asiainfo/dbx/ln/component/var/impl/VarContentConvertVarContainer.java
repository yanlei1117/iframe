package com.asiainfo.dbx.ln.component.var.impl;

import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.VarContainer;
import com.asiainfo.dbx.ln.component.var.impl.named.DecorateVarContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by yanlei on 2014/9/16.
 */
public class VarContentConvertVarContainer  extends DecorateVarContainer implements VarContainer{
    private Logger logger = LoggerFactory.getLogger(VarContentConvertVarContainer.class);
    /**
     * 获取变量值
     * 变量名：
     *   1.带有变量 ${},如：  ${address},${person.name},${person.${property}}
     *   2.中间有变量 如 ：您有${fee}个电话
     *
     * 返回值：
     *   String 如果字串中有变量，则会转换成实际值串。 如：您有${fee}个电话 ，（fee=8）,则返回结果：您有8个电话
     *   Map：map中的每一个带变量的元素会转成实际值
     *   List List 中的每一个带变量的元素会转成实际值
     *
     *
     * @param name 变量名
     * @return
     * @throws Exception
     */
    public  Object getVar(String name) throws Exception{
        if(name == null){
            return null;
        }else if(name.indexOf(VarConstant.VARPREFIX) == -1){
            return name;
        }
        name = name.trim();
        Object obj = name;
        boolean varFlag = AppVarUtils.isVar(name);
        if(varFlag){
            name = AppVarUtils.getVarOne(name);

            obj = this.getVarContainer().getVar(name);

        }

        obj = convertObject(obj);

        return obj;
    }
    private  Object convertObject(Object obj ) throws Exception{
        if(obj == null){
            return obj;
        }
        if(obj instanceof String){
            String str = (String)obj;
            boolean varFlag = AppVarUtils.isVar(str);
            if(varFlag){
                return getVar(str);
            }else{
                return getSubVar(str);
            }
        }else if(obj instanceof Map){
            Map destMap = new HashMap();
            Map srcMap = (Map)obj;
            Iterator iterator = srcMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry)iterator.next();
                Object srcKey = entry.getKey();
                Object srcValue = entry.getValue();
                Object destKey = convertObject(srcKey);
                Object destValue = convertObject(srcValue);
                destMap.put(destKey, destValue);
            }
            return destMap;
        }else if(obj instanceof List){
            List destList = new ArrayList();
            List srcList = (List)obj;
            for(Object o :srcList){
                Object newValue = convertObject(o);
                destList.add(newValue);
            }
            return destList;
        }
        return obj;
    }

    private  Object getSubVar(String str) throws Exception{
        List<String> list = AppVarUtils.getVarNames(str);
        if(list==null || list.size()==0){
            return str;
        }else{
            for(String var:list){
                Object objs = getVar(var);
                if(objs == null){
                    throw new Exception(" String var:"+var+" not exist");
                }else{
                    str =AppVarUtils.replaceVarString(str, objs.toString(), var.length());//Object obj = CommonUtils.convertVarString(str, objs.toString());
                    //return getVar(str);
                }
            }
            return str;
        }
    }

    /**
     * 缓存变量
     * @param name 变量名
     * @param obj 变量值
     * @throws Exception
     */
    public  void setVar(String name,Object obj) throws Exception{
        logger.debug("setVar name="+name+" value="+obj);
        name = trimVarChar(name);
        this.getVarContainer().setVar(name, obj);
    }
    public final  String trimVarChar(String varName){
        varName = varName.trim();
        int index = varName.indexOf(VarConstant.VARPREFIX);
        if(index != -1){
            return varName.substring(2,varName.length()-1);
        }else{
            return varName;
        }


    }


    @Override
    public boolean containsVar(String name) {
        return this.getVarContainer().containsVar(name);
    }

    @Override
    public void clear() {
        this.getVarContainer().clear();
    }
}
