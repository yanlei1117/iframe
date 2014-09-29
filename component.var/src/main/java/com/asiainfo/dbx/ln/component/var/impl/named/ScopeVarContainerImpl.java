package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.impl.factory.VarFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/16.
 */
public  abstract  class ScopeVarContainerImpl extends DecorateVarContainer implements  ScopeVarContainer,Loader {
     List<SpecialTypeVarContainer> specialTypeVarContainerList = new ArrayList<SpecialTypeVarContainer>();

    public List<SpecialTypeVarContainer> getSpecialTypeVarContainerList() {
        return specialTypeVarContainerList;
    }

    public void setSpecialTypeVarContainerList(List<SpecialTypeVarContainer> specialTypeVarContainerList) {
        this.specialTypeVarContainerList = specialTypeVarContainerList;
    }
    protected abstract String getScopeName();

    @Override
    public boolean matchVarName(String varName) {
        return varName.toUpperCase().startsWith(getScopeName());
    }


    protected String getRealVarName(String varName) {

        return varName.substring(getScopeName().length());
    }

    @Override
    public void load() throws Exception {
        for(SpecialTypeVarContainer specialTypeVarContainer:this.specialTypeVarContainerList) {
            if(DecorateVarContainer.class.isInstance(specialTypeVarContainer)){
                DecorateVarContainer decorateVarContainer  =  ((DecorateVarContainer)specialTypeVarContainer);
                if(decorateVarContainer.getVarContainer() == null){
                    ((DecorateVarContainer)specialTypeVarContainer).setVarContainer(this.getVarContainer());
                }
            }
            specialTypeMap.put(specialTypeVarContainer.getVarNamePrefix(),specialTypeVarContainer);
        }
    }

    final Map<String,SpecialTypeVarContainer> specialTypeMap = new HashMap<String,SpecialTypeVarContainer>();
    public SpecialTypeVarContainer findSpecialTypeVarContainer(String varName){
        for(SpecialTypeVarContainer specialTypeVarContainer:this.specialTypeVarContainerList) {
            if(specialTypeVarContainer.matchVarName(varName)){
                return specialTypeVarContainer;
            }
        }
        return null;
    }

    public SpecialTypeVarContainer findSpecialTypeVarContainer1(String varName){
        String varPrefix = varName;
        int index = varName.indexOf('.');
        if(index != -1&& varName.length()>index+1){
            varPrefix = varName.substring(0,index+1);
        }
        return specialTypeMap.get(varPrefix);
    }

    @Override
    public Object getVar(String name) throws Exception {
        Object result = null;
        String varName = this.getRealVarName(name);
        SpecialTypeVarContainer specialTypeVarContainer = findSpecialTypeVarContainer1(varName);
        if(specialTypeVarContainer != null){
            result = specialTypeVarContainer.getVar(varName);
        }else{
            result = this.getVarContainer().getVar(varName);
        }

        return result;

    }

    @Override
    public void setVar(String name, Object obj) throws Exception {
        String varName = this.getRealVarName(name);
        if(obj instanceof  String){
            String str = (String)obj;
            if(AppVarUtils.isVar(str)){
                obj = AppVarUtils.getVarContainer(this.getClass()).getVar(str);
            }else {
                SpecialTypeVarContainer specialTypeVarContainer = findSpecialTypeVarContainer1(str);
                if (specialTypeVarContainer != null) {
                    specialTypeVarContainer.setVar(varName, obj);
                    return;
                }
            }
        }
        this.getVarContainer().setVar(varName,obj);
    }

    @Override
    public boolean containsVar(String name) {
        String varName = this.getRealVarName(name);
        return  this.getVarContainer().containsVar(name);
    }

    @Override
    public void clear() {

    }
}
