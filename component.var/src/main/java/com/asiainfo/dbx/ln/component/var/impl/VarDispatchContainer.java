package com.asiainfo.dbx.ln.component.var.impl;
import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.component.var.VarContainer;
import com.asiainfo.dbx.ln.component.var.impl.factory.VarFactory;
import com.asiainfo.dbx.ln.component.var.impl.named.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VarDispatchContainer implements VarContainer ,Loader{

    List<ScopeVarContainer> scopeVarContainerList = new ArrayList<ScopeVarContainer>();


    ScopeVarContainer scopeDefaultVarContainer = null;



    public List<ScopeVarContainer> getScopeVarContainerList() {
        return scopeVarContainerList;
    }

    public void setScopeVarContainerList(List<ScopeVarContainer> scopeVarContainerList) {
        this.scopeVarContainerList.addAll(scopeVarContainerList);

    }

    public ScopeVarContainer getScopeDefaultVarContainer() {
        return scopeDefaultVarContainer;
    }

    public void setScopeDefaultVarContainer(ScopeVarContainer scopeDefaultVarContainer) {
        this.scopeDefaultVarContainer = scopeDefaultVarContainer;
    }

    public ScopeVarContainer findMatchedScopeVarContainer(String name){
        for(ScopeVarContainer scopeVarContainer:scopeVarContainerList){
            if(scopeVarContainer.matchVarName(name)){
                return scopeVarContainer;
            }
        }
        return null;
    }

    @Override
    public void load() throws Exception {
        for(ScopeVarContainer scopeVarContainer:scopeVarContainerList){
            specialTypeMap.put(scopeVarContainer.getVarNamePrefix(),scopeVarContainer);
        }
    }

    final Map<String,ScopeVarContainer> specialTypeMap = new HashMap<String,ScopeVarContainer>();
    public ScopeVarContainer findMatchedScopeVarContainer1(String varName){
        String varPrefix = varName;
        int index = varName.indexOf('.');
        if(index != -1){
            varPrefix = varName.substring(0,index);
        }
        return specialTypeMap.get(varPrefix);
    }

    @Override
    public Object getVar(String name) throws Exception {
        Object result = null;
        ScopeVarContainer  scopeVarContainer = findMatchedScopeVarContainer1(name);
        if(scopeVarContainer != null){
            result =  scopeVarContainer.getVar(name);
        }else{
            result =  this.getScopeDefaultVarContainer().getVar(name);
        }


        if(result != null && VarFactory.class.isAssignableFrom(result.getClass())){
            result = ((VarFactory)result).getVarValue();
        }
        return result;
    }

    @Override
    public void setVar(String name, Object obj) throws Exception {
        Object result = null;
        ScopeVarContainer  scopeVarContainer = findMatchedScopeVarContainer1(name);
        if(scopeVarContainer != null){
             scopeVarContainer.setVar(name, obj);
        }else{
            this.getScopeDefaultVarContainer().setVar(name, obj);
        }


    }

    @Override
    public boolean containsVar(String name) {
        Object result = null;
        ScopeVarContainer  scopeVarContainer = findMatchedScopeVarContainer(name);
        if(scopeVarContainer != null){
           return  scopeVarContainer.containsVar(name);
        }else{
            return   this.getScopeDefaultVarContainer().containsVar(name);
        }
    }

    @Override
    public void clear() {
        for(ScopeVarContainer scopeVarContainer:scopeVarContainerList){
            scopeVarContainer.clear();
        }
        this.getScopeDefaultVarContainer().clear();
    }
}
