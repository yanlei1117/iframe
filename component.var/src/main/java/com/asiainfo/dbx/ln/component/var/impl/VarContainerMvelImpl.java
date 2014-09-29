package com.asiainfo.dbx.ln.component.var.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/7/11.
 */
public class VarContainerMvelImpl extends VarContainerImpl{
    private final Logger logger = LoggerFactory.getLogger(VarContainerMvelImpl.class);
    @Override
    public Object getVar(String name) throws Exception {
        Object obj = MVEL.eval(name,this.varMap);
        logger.debug("{} getVar: name={} obj = {} obj.class={} varMap:{}",this,name,obj,obj==null?null:obj.getClass(),varMap );
        return obj;
    }
}
