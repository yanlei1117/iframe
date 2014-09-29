package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.var.VarConstant;

/**
 * Created by yaneli on 2014/9/15.
 */
public class SpecialTypeJsonVarContainer extends DecorateVarContainer implements SpecialTypeVarContainer {

    @Override
    public boolean matchVarName(String varName) {
        return varName.toUpperCase().startsWith(VarConstant.VAR_PREFIX_JSON);
    }
    @Override
    public String getVarNamePrefix() {
        return  VarConstant.VAR_PREFIX_JSON;
    }
    @Override
    public Object getVar(String name) throws Exception {
        String jsonStr = name.substring(VarConstant.VAR_PREFIX_JSON.length());
        Object obj  =  convertToObject(jsonStr);

        //String varName = "var"+ AppStringUtils.getUUID();
        //this.setVar(varName,obj);
        //return this.getVar(varName);
        return obj;
    }

    @Override
    public void setVar(String name, Object obj) throws Exception {
        String expression = (String)obj;
         String newExpression =expression.substring(VarConstant.VAR_PREFIX_DATE.length());
        Object newObj = convertToObject(newExpression);
        this.getVarContainer().setVar(name,newObj);
    }

    private Object convertToObject(String jsonStr) throws Exception{
        String standardJson = AppJsonUtils.formatToStandardJsonString(jsonStr);
        Object obj  =  AppJsonUtils.jsonToObj(standardJson);
        return obj;
    }
    @Override
    public boolean containsVar(String name) {
        return false;
    }

    @Override
    public void clear() {

    }
}
