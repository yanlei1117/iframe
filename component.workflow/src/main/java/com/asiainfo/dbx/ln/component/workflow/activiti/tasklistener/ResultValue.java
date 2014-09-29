package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultValue {
	Logger logger = LoggerFactory.getLogger(ResultValue.class);
	 Map<String,Object> getResultMap(){
		Map<String,Object> params = new HashMap<String,Object>();
		
		 
		params.put("result", getInput("please type task result :true/false?"));
		
		
		return params;
		
	}
	
	 Map<String,Object> getResultAndLoopMap(){
		Map<String,Object> params = new HashMap<String,Object>();
	
		 
			params.put("result", getInput("please type task result :true/false?"));
			params.put("loop", getInput("please type task loop :true/false?"));
		
		
		return params;
		
	}
	
	Scanner scanner = new Scanner(System.in);
	private String getInput(String tip){
		logger.info(tip);
		String line = scanner.nextLine();
		line = line.trim();
		if(line.equalsIgnoreCase("true")||line.equalsIgnoreCase("false")){
			return line;
		}else{
			return getInput(tip);
		}
	}
}
