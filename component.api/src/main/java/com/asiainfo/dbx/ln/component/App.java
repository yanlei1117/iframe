package com.asiainfo.dbx.ln.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.dbx.ln.component.spring.ApplicationContextHolder;
import com.asiainfo.dbx.ln.component.spring.impl.ApplicationContextHolderParentFixed;

/**
 * Hello world!
 *
 */
public class App 
{private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main( String[] args )
  {
      try {
          ApplicationContextHolder holder = new ApplicationContextHolderParentFixed();
          logger.info("ok");
          //ApplicationContext applicationContext = holder.instanceApplicationContext("workFlow", "classpath*:/config/workflow/activiti*.xml");
          //Object obj = applicationContext.getBean("processEngine");
          //Assert.assertNotNull(obj);
      }catch(Throwable e){
          e.printStackTrace();
      }
  }
}
