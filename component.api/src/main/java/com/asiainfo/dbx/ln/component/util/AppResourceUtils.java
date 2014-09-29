package com.asiainfo.dbx.ln.component.util;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class AppResourceUtils {
	private static ResourcePatternResolver  resourcePatternResolver = new PathMatchingResourcePatternResolver();
	public static Resource [] getResources(String resourcePath) throws IOException{
		return resourcePatternResolver.getResources(resourcePath);
	}
	public static Resource   getResource(String resourcePath) throws IOException{
		 Resource [] resources =  getResources(resourcePath);
		 if(resources != null && resources.length>0){
			 return resources[0];
		 }else{
			 return null;
		 }
	}


}
