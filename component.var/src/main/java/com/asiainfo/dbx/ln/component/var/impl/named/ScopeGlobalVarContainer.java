package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.var.VarConstant;

/**
 * Created by yanlei on 2014/9/16.
 */
public class ScopeGlobalVarContainer extends  ScopeVarContainerImpl{
    @Override
    protected String getScopeName() {
        return VarConstant.GLOBAL;
    }
    @Override
    public String getVarNamePrefix() {
        return  VarConstant.GLOBAL;
    }
}
