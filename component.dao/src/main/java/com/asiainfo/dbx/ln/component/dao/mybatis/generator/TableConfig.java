package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

/**
 * Created by yanlei on 2014/8/1.
 */
public class TableConfig {
    private String tableName;
    private String javaPojoName;
    private String schema;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getJavaPojoName() {
        return javaPojoName;
    }

    public void setJavaPojoName(String javaPojoName) {
        this.javaPojoName = javaPojoName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
