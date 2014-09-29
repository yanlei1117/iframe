package com.asiainfo.dbx.ln.component.spring.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.asiainfo.dbx.ln.component.spring.ApplicationContextHolder;

/**
 *
 */
public class ApplicationContextHolderParentFixed extends DefaultApplicationContextHolder implements ApplicationContextHolder {
	private final Logger logger = LoggerFactory.getLogger(ApplicationContextHolderParentFixed.class);
	ApplicationContext parentApplicationContext;
	 public ApplicationContextHolderParentFixed() throws Exception{

		 parentApplicationContext =  new GenericXmlApplicationContext("classpath*:/config/spring/base/component*.xml","classpath*:/config/spring/component*.xml","classpath*:/config/spring/base/business*.xml","classpath*:/config/spring/business*.xml");
		 lastApplicationContext = parentApplicationContext;
		 put("root",parentApplicationContext);
	 }
	public ApplicationContextHolderParentFixed (String ... parentResourcePath ){
		parentApplicationContext =  new GenericXmlApplicationContext(parentResourcePath);
		lastApplicationContext = parentApplicationContext;
		put("root",parentApplicationContext);
	}
	 
	/* (non-Javadoc)
	 * @see app.base.component.spring.impl.ApplicationContextHolder#instanceApplicationContext(java.lang.String, java.lang.String)
	 */
	public ApplicationContext instanceApplicationContext(String contextName,String ... resourcePath){
		 GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
		 applicationContext.setParent(parentApplicationContext);
		 applicationContext.load(resourcePath);
		 applicationContext.refresh();
		 this.put(contextName, applicationContext);
		 lastApplicationContext = applicationContext;
		return lastApplicationContext;
	}
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(!applicationContextMap.values().contains(applicationContext)){
            put(System.currentTimeMillis()+"",applicationContext);
            if(lastApplicationContext != null){
                if(AbstractApplicationContext.class.isAssignableFrom(applicationContext.getClass())){
                    ((AbstractApplicationContext)applicationContext).setParent(parentApplicationContext);
                }
            }
            lastApplicationContext = applicationContext;
        }
    }
}
