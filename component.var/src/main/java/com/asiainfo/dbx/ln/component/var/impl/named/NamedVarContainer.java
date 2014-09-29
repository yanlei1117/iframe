package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.var.VarContainer;

/**
 * Created by yanlei on 2014/9/15.
 */
public interface NamedVarContainer extends VarContainer{
    public boolean matchVarName(String varName);
    public String getVarNamePrefix();
}
