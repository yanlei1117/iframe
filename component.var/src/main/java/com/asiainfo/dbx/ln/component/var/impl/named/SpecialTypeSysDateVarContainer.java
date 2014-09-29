package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.VarContainer;

import java.util.Date;

/**
 * Created by yanlei on 2014/9/15.
 */
public class SpecialTypeSysDateVarContainer implements SpecialTypeVarContainer{
    @Override
    public boolean matchVarName(String varName){
        return VarConstant.VAR_NAMED_SYSDATE.equalsIgnoreCase(varName);
    }
    @Override
    public String getVarNamePrefix() {
        return  VarConstant.VAR_NAMED_SYSDATE;
    }
    @Override
    public Object getVar(String name) throws Exception {
        return new Date();
    }

    @Override
    public void setVar(String name, Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsVar(String name) {
        return false;
    }

    @Override
    public void clear() {

    }
}
