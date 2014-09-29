package com.asiainfo.dbx.ln.component.spring.impl;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.asiainfo.dbx.ln.component.spring.ApplicationContextHolder;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public abstract class DefaultApplicationContextHolder implements ApplicationContextHolder{

    protected Map<String,ApplicationContext> applicationContextMap = new java.util.concurrent.ConcurrentHashMap<String,ApplicationContext>();
	protected ApplicationContext lastApplicationContext;

	public DefaultApplicationContextHolder() {

	}

	public ApplicationContext getApplicationContext(String contextName) {
		return applicationContextMap.get(contextName);
	}

	public ApplicationContext getApplicationContext() {
		return lastApplicationContext;
	}
	void put(String contextName,ApplicationContext applicationContext){
		applicationContextMap.put(contextName, applicationContext);
	}


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(!applicationContextMap.values().contains(applicationContext)){
            put(System.currentTimeMillis()+"",applicationContext);
            if(lastApplicationContext != null){
                 if(AbstractApplicationContext.class.isAssignableFrom(applicationContext.getClass())){
                    ((AbstractApplicationContext)applicationContext).setParent(lastApplicationContext);
                }/*else if(GenericApplicationContext.class.isAssignableFrom(applicationContext.getClass())){
                    ((GenericApplicationContext)applicationContext).setParent(lastApplicationContext);
                }else if(DefaultListableBeanFactory.class.isAssignableFrom(applicationContext.getClass())){
                    ((DefaultListableBeanFactory)applicationContext).setParentBeanFactory(lastApplicationContext);
                }*/
            }
            lastApplicationContext = applicationContext;

        }
    }

    @Override
    public void registerBean(String beanName,String className,String scope,Map<String,String> propertyMap) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)lastApplicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(className);
        beanDefinitionBuilder.setScope(scope);
        if(AppValidationUtils.notNull(propertyMap)){
            Iterator<String> iterator = propertyMap.keySet().iterator();
            while(iterator.hasNext()){
                String propertyName = iterator.next();
                String value = propertyMap.get(propertyName);
                if(AppValidationUtils.notNull(value)){
                   if(value.startsWith("ref.")||value.startsWith("REF.")){
                       beanDefinitionBuilder.addPropertyReference(propertyName,value.substring(4));
                   }else{
                       beanDefinitionBuilder.addPropertyValue(propertyName,value);
                   }
                }

            }
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        defaultListableBeanFactory.registerBeanDefinition(beanName,beanDefinition);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return lastApplicationContext.getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return lastApplicationContext.getBean(name,requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return lastApplicationContext.getBean(requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return lastApplicationContext.getBean(name,args);
    }

    @Override
    public boolean containsBean(String name) {
        return lastApplicationContext.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return lastApplicationContext.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return lastApplicationContext.isPrototype(name);
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
        return lastApplicationContext.isTypeMatch(name,targetType);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return lastApplicationContext.getType(name);
    }

    @Override
    public String[] getAliases(String name) {
        return lastApplicationContext.getAliases(name);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return ((ListableBeanFactory)lastApplicationContext).containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return ((ListableBeanFactory)lastApplicationContext).getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return ((ListableBeanFactory)lastApplicationContext).getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return ((ListableBeanFactory)lastApplicationContext).getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return ((ListableBeanFactory)lastApplicationContext).getBeanNamesForType(type,includeNonSingletons,allowEagerInit);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
          return ((ListableBeanFactory)lastApplicationContext).getBeansOfType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return ((ListableBeanFactory)lastApplicationContext).getBeansOfType(type,includeNonSingletons,allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return ((ListableBeanFactory)lastApplicationContext).getBeanNamesForAnnotation(annotationType);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return ((ListableBeanFactory)lastApplicationContext).getBeansWithAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return ((ListableBeanFactory)lastApplicationContext).findAnnotationOnBean(beanName,annotationType);
    }
}