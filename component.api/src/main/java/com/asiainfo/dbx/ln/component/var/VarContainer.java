package com.asiainfo.dbx.ln.component.var;

/**
 * Created by Administrator on 2014/7/10.
 */
public interface VarContainer {
    public Object getVar(String name) throws Exception;
    public void setVar(String name,Object obj) throws Exception;
    public boolean containsVar(String name);
    public void clear();
}
