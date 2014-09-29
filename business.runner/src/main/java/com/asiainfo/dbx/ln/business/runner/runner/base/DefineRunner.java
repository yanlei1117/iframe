package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/9/15.
 */
public class DefineRunner  extends Runner {

    private static final Logger logger = LoggerFactory.getLogger(DefineRunner.class);
    String varName = null;

    String varValue = null;




    public String getVarName() {
        return varName;
    }


    public void setVarName(String varName) {
        this.varName = varName;
    }


    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    @Override
    public void run() throws Exception{
        // TODO Auto-generated method stub
        if(AppValidationUtils.notEmpty(this.varName)){
            if(this.getVarValue() != null){
                AppVarUtils.getVarContainer(this.getClass()).setVar(this.varName, this.getVarValue());
                logger.debug("DefineRunner varName:{},  value:{} (class={} ),desc={}  finish",this.varName,this.varValue,varValue.getClass(),this.getDesc());
            }else{
                logger.debug("DefineRunner varName:{} value: {}, desc={},finish",this.varName,this.varValue,this.getDesc());
            }
        }else{
            logger.error(" DefineRunner varName is null,varName:{},  value:{},desc={}",this.varName,this.varValue,this.getDesc());
        }
    }

    @Override
    public String toString() {
        return "\nDefineRunner{" +
                "varName='" + varName + '\'' +
                ", varValue='" + varValue + '\'' +
                '}';
    }
}
