package com.asiainfo.dbx.ln.component.log.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.component.util.AppEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.asiainfo.dbx.ln.component.log.LogConfiguration;
import com.asiainfo.dbx.ln.component.util.AppResourceUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

public class LogBackConfiguration implements Loader{
	private static final Logger logger = LoggerFactory.getLogger(LogBackConfiguration.class);
	private String xmlEndString ="</configuration>"; 
	private String logBackXmlPath = null;
	private String includeLogBackXmlPaths = null;
	
	public String getLogBackXmlPath() {
		return logBackXmlPath;
	}

	public void setLogBackXmlPath(String logBackXmlPath) {
		this.logBackXmlPath = logBackXmlPath;
	}

	private String charset = "UTF-8";
	

	 
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getIncludeLogBackXmlPaths() {
		return includeLogBackXmlPaths;
	}

	public void setIncludeLogBackXmlPaths(String includeLogBackXmlPaths) {
		this.includeLogBackXmlPaths = includeLogBackXmlPaths;
	}

	public  void load() throws Exception{
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
	    
		
	     Resource resource =  AppResourceUtils.getResource(this.getLogBackXmlPath());
         if(resource == null){
            logger.error("not found logback config file :"+this.getLogBackXmlPath());
         }else {
             logger.info("logback.xml:--->{}", resource);
             InputStream inputStream = resource.getInputStream();
             if (this.getIncludeLogBackXmlPaths() != null) {
                 Resource resources[] = AppResourceUtils.getResources(this.getIncludeLogBackXmlPaths());
                 if (AppValidationUtils.notEmpty(resources)) {
                     inputStream = mergeLogBackXmlFile(inputStream, resources);
                 }
             }

             JoranConfigurator configurator = new JoranConfigurator();
             configurator.setContext(context);
             // Call context.reset() to clear any previous configuration, e.g. default
             // configuration. For multi-step configuration, omit calling context.reset().
             context.reset();
             configurator.doConfigure(inputStream);
         }
//	    } catch (JoranException je) {
//	      // StatusPrinter will handle this
//	    }
	}
	
	private InputStream mergeLogBackXmlFile(InputStream is,Resource resources[]) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(is,this.getCharset()));
		
		
		StringBuilder stringBuilder = new StringBuilder(); 
		String line =null;
		while((line =br.readLine())!= null){
			if(line.trim().toLowerCase().endsWith(xmlEndString)){
				line = line.substring(0,line.length()-xmlEndString.length());
				if(AppValidationUtils.notEmpty(line)){
					stringBuilder.append(line+"\n");
				}
				for(Resource resource:resources){
					String filePath = resource.getFile().getAbsolutePath();
					int jarIndex = filePath.indexOf(".jar!");
					if(jarIndex != -1){
						stringBuilder.append("<include resource=\""+filePath.substring(jarIndex+".jar!".length())+"\"/>\n");
					}else{
						stringBuilder.append("<include file=\""+filePath+"\"/>\n");
					}
				}
				stringBuilder.append(xmlEndString+"\n");
			}else{
                stringBuilder.append(line + "\n");

                /*if(AppEnvironment.isFormalEnvironment() && line.trim().equals("<appender-ref ref=\"STDOUT\" />")){//生产环境下不输出到控制台
                }else {
                    stringBuilder.append(line + "\n");
                }*/
			}
		}
		

		logger.info("generate logback xml:{}",stringBuilder.toString());
		br.close();
		
		return new ByteArrayInputStream(stringBuilder.toString().getBytes(this.getCharset()));
	}
}
