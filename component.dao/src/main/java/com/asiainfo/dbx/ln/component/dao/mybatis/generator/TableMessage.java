package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * Created by yanlei on 2014/8/11.
 */
public class TableMessage {
    String tableName;
    TopLevelClass topLevelClass;
    IntrospectedTable introspectedTable;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TopLevelClass getTopLevelClass() {
        return topLevelClass;
    }

    public void setTopLevelClass(TopLevelClass topLevelClass) {
        this.topLevelClass = topLevelClass;
    }

    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }
}
