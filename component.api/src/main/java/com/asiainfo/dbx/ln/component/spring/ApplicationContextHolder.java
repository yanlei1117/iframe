package com.asiainfo.dbx.ln.component.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public interface ApplicationContextHolder extends ListableBeanFactory,ApplicationContextAware{
	
	public abstract ApplicationContext getApplicationContext(String contextName);

	public abstract ApplicationContext getApplicationContext();

	public abstract ApplicationContext instanceApplicationContext(
            String contextName, String... resourcePath);

    public void registerBean(String beanName,String className,String scope,Map<String,String> propertyMap) ;
}