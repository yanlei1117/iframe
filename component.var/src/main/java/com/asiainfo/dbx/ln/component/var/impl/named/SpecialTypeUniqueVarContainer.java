package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.var.VarConstant;

import java.util.UUID;

/**
 * Created by yanlei on 2014/9/15.
 */
public class SpecialTypeUniqueVarContainer implements  SpecialTypeVarContainer{
    @Override
    public boolean matchVarName(String varName){
        return VarConstant.VAR_NAMED_UNIQUE.equalsIgnoreCase(varName);
    }

    @Override
    public String getVarNamePrefix() {
        return  VarConstant.VAR_NAMED_UNIQUE;
    }

    @Override
    public Object getVar(String name) throws Exception {
        String uuid =  UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").toUpperCase();

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
