package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.var.VarConstant;

/**
 * Created by yanlei on 2014/9/16.
 */
public class ScopeDefaultVarContainer extends  ScopeVarContainerImpl{
    @Override
    protected String getScopeName() {
        return "";
    }

    @Override
    public boolean matchVarName(String varName) {
        return true;
    }
    @Override
    public String getVarNamePrefix() {
        return "";
    }
    @Override
    protected String getRealVarName(String varName) {
        return varName;
    }
}
