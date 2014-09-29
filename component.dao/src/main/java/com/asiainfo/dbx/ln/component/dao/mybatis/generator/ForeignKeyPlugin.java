package com.asiainfo.dbx.ln.component.dao.mybatis.generator;


import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.internal.db.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 *
 * 由表间的外键关系，生成模型的关联关系
 *
 * Created by yanlei on 2014/8/11.
 */
public class ForeignKeyPlugin  extends PluginAdapter {
    private Logger logger = LoggerFactory.getLogger(ForeignKeyPlugin.class);


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        generatorCode();
        return super.contextGenerateAdditionalJavaFiles();
    }


    Map<String,List<ForeignKey>> foreignKeyMap = new HashMap<String,List<ForeignKey>>();


    Map<String ,TableMessage> candidateTableMessageMap = new HashMap<String ,TableMessage>();


    private List<ForeignKey> getForeignKeyFromDB( IntrospectedTable introspectedTable){
        List<ForeignKey> foreignKeyList = null;
        Connection connection = null;
        try {
            connection = ConnectionFactory.getInstance().getConnection(introspectedTable.getContext().getJdbcConnectionConfiguration());

            ResultSet resultSet = connection.getMetaData().getImportedKeys(introspectedTable.getTableConfiguration().getCatalog(),introspectedTable.getTableConfiguration().getSchema(),introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

            if(AppValidationUtils.notNull(resultSet)){
                foreignKeyList = new ArrayList<ForeignKey>();
                while(resultSet.next()){//获取表的外键
                    String foreignTableName =  resultSet.getString("FKTABLE_NAME");//从表表名
                    String foreignColumnName =  resultSet.getString("FKCOLUMN_NAME");//从表列名 --对应主表主键
                    String primaryKeyColumnName =  resultSet.getString("PKCOLUMN_NAME");//主表主键
                    String primaryKeyTableName =  resultSet.getString("PKTABLE_NAME");//主表表名
                    logger.info("foreignTableName:{},foreignColumnName:{},primaryKeyTableName:{},primaryKeyColumnName:{}",foreignTableName,foreignColumnName,primaryKeyTableName,primaryKeyColumnName);
                    ForeignKey foreignKey = new ForeignKey();

                    foreignKey.setForeignColumnName(foreignColumnName);
                    foreignKey.setForeignTableName(foreignTableName);
                    foreignKey.setPrimaryKeyColumnName(primaryKeyColumnName);
                    foreignKey.setPrimaryKeyTableName(primaryKeyTableName);
                    foreignKeyList.add(foreignKey);
                }
            }

        }catch(Exception e){
            logger.error("",e);
        }finally{
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // ignore
                    ;
                }
            }
        }
        return foreignKeyList;
    }
    List<ForeignKey> foreignKeyList = new ArrayList<ForeignKey>();
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        TableMessage tableMessage  = new TableMessage();
        tableMessage.setTableName(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        tableMessage.setTopLevelClass(topLevelClass);
        tableMessage.setIntrospectedTable(introspectedTable);
        candidateTableMessageMap.put(tableMessage.getTableName(),tableMessage);
        List<ForeignKey> foreignKeyList1 =  getForeignKeyFromDB(introspectedTable);//当前表的外键信息
        if(AppValidationUtils.notNull(foreignKeyList1)) {
            foreignKeyList.addAll(foreignKeyList1);
        }
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    private Map<String,Interface> interfaceMap = new HashMap<String,Interface>();
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaceMap.put(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime(),interfaze);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    public void generatorCode(){
        if(AppValidationUtils.notEmpty(this.foreignKeyList)){
            for(ForeignKey foreignKey:foreignKeyList){
                TableMessage tableMessage =   this.candidateTableMessageMap.get(foreignKey.getPrimaryKeyTableName());
                if(AppValidationUtils.notNull(tableMessage)){
                    foreignKey.setPrimaryKeyIntrospectedTable(tableMessage.getIntrospectedTable());
                    foreignKey.setPrimaryKeyTopLevelClass(tableMessage.getTopLevelClass());
                }
                tableMessage =   this.candidateTableMessageMap.get(foreignKey.getForeignTableName());
                if(AppValidationUtils.notNull(tableMessage)){
                    foreignKey.setForeignKeyIntrospectedTable(tableMessage.getIntrospectedTable());
                    foreignKey.setForeignKeyTopLevelClass(tableMessage.getTopLevelClass());
                }

                Document document = this.candidateDocumentMap.get(foreignKey.getPrimaryKeyTableName());
                if(AppValidationUtils.notNull(document)){
                    foreignKey.setPrimaryKeyDocument(document);
                }
                document = this.candidateDocumentMap.get(foreignKey.getForeignTableName());
                if(AppValidationUtils.notNull(document)){
                    foreignKey.setForeignKeyDocument(document);
                }

                Interface daoMapperInterface = this.interfaceMap.get(foreignKey.getPrimaryKeyTableName());
                if(AppValidationUtils.notNull(daoMapperInterface)){
                    foreignKey.setPrimaryDaoMapperInterface(daoMapperInterface);
                }
                daoMapperInterface = this.interfaceMap.get(foreignKey.getForeignTableName());
                if(AppValidationUtils.notNull(daoMapperInterface)){
                    foreignKey.setForeignDaoMapperInterface(daoMapperInterface);
                }

                if(foreignKey.isOKToGenerateModel()){
                    foreignKey.generateForModel();
                }
                if(foreignKey.isOKToGenerateXML()){
                    foreignKey.generateXML();
                }
            }
        }
        this.foreignKeyList = null;
        this.candidateDocumentMap= null;
        this.candidateTableMessageMap= null;

    }



    private Map<String,Document> candidateDocumentMap = new HashMap<String,Document>();
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        candidateDocumentMap.put(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime(),document);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
