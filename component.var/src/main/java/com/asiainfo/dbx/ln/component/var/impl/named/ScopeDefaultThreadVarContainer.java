package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.VarContainer;

/**
 * Created by yanlei on 2014/9/16.
 */
public class ScopeDefaultThreadVarContainer extends ScopeDefaultVarContainer{

    @Override
    public VarContainer getVarContainer() {
        return varThreadLocal.get();
    }

    ThreadLocal<VarContainer> varThreadLocal =new ThreadLocal<VarContainer>(){
        @Override
        protected VarContainer initialValue() {
            return findVarContainer();
        }
    };

    public VarContainer findVarContainer(){
        return null;
    }
    @Override
    public String getVarNamePrefix() {
        return "";
    }

}
