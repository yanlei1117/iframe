package com.asiainfo.dbx.ln.business.service;

/**
 * Created by yanlei on 2014/8/13.
 */
public class DimData {

    String id;
    String label;
    String parentId;//父ID
    String status;//状态 1有效 0 无效
    String spell;//拼音码
    String haveChildren="0";//没有子项





    public static DimData getEmptyDimData(){
        DimData dimData  = new DimData();
        dimData.setId("");
        dimData.setLabel("请选择");
        dimData.setParentId("");
        dimData.setStatus("");
        dimData.setSpell("");
        return dimData;
    }
    public String getSpell() {
        return spell;
    }
    public void setSpell(String spell) {
        this.spell = spell;
    }





    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getHaveChildren() {
        return haveChildren;
    }
    public void setHaveChildren(String haveChildren) {
        this.haveChildren = haveChildren;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
