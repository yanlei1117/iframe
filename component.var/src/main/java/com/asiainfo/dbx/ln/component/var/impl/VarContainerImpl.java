package com.asiainfo.dbx.ln.component.var.impl;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.VarContainer;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.expression.DefaultResolver;
import org.apache.commons.beanutils.expression.Resolver;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 */

public class VarContainerImpl implements VarContainer {
	private static Logger logger = LoggerFactory.getLogger(VarContainerImpl.class);
	//Map<String,Object> varMap = new java.util.concurrent.ConcurrentHashMap<String,Object>();
    Map<String,Object> varMap = new HashMap<String,Object>(){};

    public VarContainerImpl(){
      init();
    }
    public void init(){
        varMap.put(VarConstant.VAR_NAMED_SESSION,new HashMap());
        varMap.put(VarConstant.VAR_NAMED_REQUEST,new HashMap());
    }
	Resolver  resolver = new DefaultResolver(); 
	public Object getVar(String name) throws Exception{
		Object obj  =  PropertyUtils.getProperty(varMap, name);
		logger.debug("{} getVar( name={}, obj = {}) obj.class={} varMap:{}",this.getClass().getSimpleName(),name,obj,obj==null?null:obj.getClass(),varMap );


		return obj;
	}
	public void setVar(String name,Object obj) throws Exception{
		
		if(false && obj == null && !resolver.hasNested(name)){
				if(containsVar(name)){
					varMap.remove(name);
				}
		}else{

			PropertyUtils.setProperty(varMap, name, obj);
			logger.debug("{} setVar( name={}, obj = {})",this.getClass().getSimpleName(),name,obj);
			
		}
	}
	public boolean containsVar(String name) {
		// TODO Auto-generated method stub
		return varMap.containsKey(name);
	}
	public void clear() {
		// TODO Auto-generated method stub
        Object session = varMap.get(VarConstant.VAR_NAMED_SESSION);
        Object request = varMap.get(VarConstant.VAR_NAMED_REQUEST);
		varMap.clear();
        varMap.put(VarConstant.VAR_NAMED_SESSION,session);
        varMap.put(VarConstant.VAR_NAMED_REQUEST,request);
	}
	
}
