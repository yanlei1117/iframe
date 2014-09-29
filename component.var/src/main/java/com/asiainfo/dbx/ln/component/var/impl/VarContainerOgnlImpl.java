package com.asiainfo.dbx.ln.component.var.impl;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import ognl.Ognl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/7/28.
 */
public class VarContainerOgnlImpl extends VarContainerImpl {
    private ConcurrentHashMap<String,Object> expressionMap = new ConcurrentHashMap<String,Object>();
    @Override
    public Object getVar(String name) throws Exception {
        Object tree = expressionMap.get(name);
        if(AppValidationUtils.isNull(tree)){
            tree = Ognl.parseExpression(name);//表达式多次使用时，先编译比较好,供以后多次调用
            expressionMap.putIfAbsent(name,tree);
        }
        Object result = Ognl.getValue(tree, this.varMap);//取root的属性
        return result;
    }

}
