package com.asiainfo.dbx.ln.component.spring.impl;

import java.util.Map;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.asiainfo.dbx.ln.component.spring.ApplicationContextHolder;

public class ApplicationContextHolderParentLast extends DefaultApplicationContextHolder implements ApplicationContextHolder {


	public ApplicationContext instanceApplicationContext(String contextName,String ... resourcePath){
		
		GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
		if(lastApplicationContext != null){
			applicationContext.setParent(lastApplicationContext);
		}
		 applicationContext.load(resourcePath);
		 applicationContext.refresh();
		 this.put(contextName, applicationContext);
		 lastApplicationContext = applicationContext;
		return lastApplicationContext;
	
	
	}
}
