package com.asiainfo.dbx.ln.business.transport.http.listener;

import com.asiainfo.dbx.ln.component.api.AppComponent;

import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by yanlei on 2014/9/3.
 */

public class SpringContextLoadListener extends ContextLoaderListener {
    private Logger logger = LoggerFactory.getLogger(SpringContextLoadListener.class);
    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext webApplicationContext =  initWebApplicationContext(event.getServletContext());
        //AppSpringUtils.getApplicationContextHolder().setApplicationContext(webApplicationContext);
    }

    @Override
    protected ApplicationContext loadParentContext(ServletContext servletContext)  {
        try {
            AppComponent.start();
            return AppSpringUtils.getApplicationContextHolder().getApplicationContext();
        }catch(Throwable e){
            logger.error("",e);
            throw new RuntimeException(e);
        }
    }
}
