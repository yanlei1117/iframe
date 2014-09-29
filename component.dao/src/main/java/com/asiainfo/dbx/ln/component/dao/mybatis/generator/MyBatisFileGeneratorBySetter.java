package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/8/1.
 */
public class MyBatisFileGeneratorBySetter implements MyBatisFileGenerator{
    private Logger logger = LoggerFactory.getLogger(MyBatisFileGeneratorBySetter.class);
    private String connectionURL = null;


    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    private String driverClass = null;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    String modelPackageNameGenerated=null;
    String mapperDaoPackageNamedGenerated = null;
    String mapperXmlPackageNamedGenerated = null;
    String repository;
    String container;


    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }


    public String getModelPackageNameGenerated() {
        return modelPackageNameGenerated==null? MyBatisMapper.DEFAULT_MODEL_PACKAGE+"."+this.getRepository()+"."+this.getContainer():modelPackageNameGenerated;
    }

    public void setModelPackageNameGenerated(String modelPackageNameGenerated) {
        this.modelPackageNameGenerated = modelPackageNameGenerated;
    }

    public String getMapperDaoPackageNamedGenerated() {
        return mapperDaoPackageNamedGenerated==null?MyBatisMapper.DEFAULT_MODEL_PACKAGE+"."+this.getRepository()+"."+this.getContainer():mapperDaoPackageNamedGenerated;
    }

    public void setMapperDaoPackageNamedGenerated(String mapperDaoPackageNamedGenerated) {
        this.mapperDaoPackageNamedGenerated = mapperDaoPackageNamedGenerated;
    }

    public String getMapperXmlPackageNamedGenerated() {
        return mapperXmlPackageNamedGenerated==null?MyBatisMapper.DEFAULT_MODEL_PACKAGE+"."+this.getRepository()+"."+this.getContainer():mapperXmlPackageNamedGenerated;
    }

    public void setMapperXmlPackageNamedGenerated(String mapperXmlPackageNamedGenerated) {
        this.mapperXmlPackageNamedGenerated = mapperXmlPackageNamedGenerated;
    }

    String targetDir = null;

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }
    private List<TableConfig> tableConfigsList = null;

    public List<TableConfig> getTableConfigsList() {
        return tableConfigsList;
    }

    public void setTableConfigsList(List<TableConfig> tableConfigsList) {
        this.tableConfigsList = tableConfigsList;
    }

    private void configJavaTypeResolver(Context context){
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty("forceBigDecimals","false");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);
    }
    private void configJDBCConnection(Context context){
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(this.getConnectionURL());
        jdbcConnectionConfiguration.setDriverClass(this.getDriverClass());
        jdbcConnectionConfiguration.setUserId(this.getUserId());
        jdbcConnectionConfiguration.setPassword(this.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
    }
    private void configCommentGenerator(Context context){
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressDate","true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
    }
    private void configJavaModelGenerator(Context context){
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(this.getModelPackageNameGenerated());
        javaModelGeneratorConfiguration.setTargetProject(this.getTargetDir());
        javaModelGeneratorConfiguration.addProperty("enableSubPackages","true");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
    }

    private void configSqlMapGenerator(Context context){
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage(this.getMapperXmlPackageNamedGenerated());
        sqlMapGeneratorConfiguration.setTargetProject(this.getTargetDir());
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages","true");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

    }
    private void configJavaClientGenerator (Context context){
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetPackage(this.getMapperDaoPackageNamedGenerated());
        javaClientGeneratorConfiguration.setTargetProject(this.getTargetDir());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages","true");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
    }

    private void configTable(Context context,TableConfig tableConfig){
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        if(AppValidationUtils.notNull(tableConfig.getSchema())){
            tableConfiguration.setSchema(tableConfig.getSchema());
        }
        tableConfiguration.setDomainObjectName(tableConfig.getJavaPojoName());
        tableConfiguration.setTableName(tableConfig.getTableName());
        context.addTableConfiguration(tableConfiguration);
    }
    private List<String> pluginClassList = null;

    public List<String> getPluginClassList() {
        return pluginClassList;
    }

    public void setPluginClassList(List<String> pluginClassList) {
        this.pluginClassList = pluginClassList;
    }

    private void configPlugin(Context context){
        if(AppValidationUtils.notNull(pluginClassList)){
            for(String className:this.pluginClassList){
                PluginConfiguration pluginConfiguration = new PluginConfiguration();
                pluginConfiguration.setConfigurationType(className);
                context.addPluginConfiguration(pluginConfiguration);
            }
        }else {
            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("com.asiainfo.dbx.ln.component.dao.mybatis.generator.PaginationPlugin");
            context.addPluginConfiguration(pluginConfiguration);
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("com.asiainfo.dbx.ln.component.dao.mybatis.generator.ForeignKeyPlugin");
            context.addPluginConfiguration(pluginConfiguration);


            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("com.asiainfo.dbx.ln.component.dao.mybatis.generator.MyBatisMapperModifyPlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }
    }
    @Override
    public boolean generate() {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;




            Context context = new Context(ModelType.CONDITIONAL);


            context.setTargetRuntime("MyBatis3");
            context.setId("MyBatisGenerator");
            configPlugin(context);
            configCommentGenerator(context);
            configJDBCConnection(context);
            configJavaTypeResolver(context);
            configJavaModelGenerator(context);
            configSqlMapGenerator(context);
            configJavaClientGenerator(context);
            if(AppValidationUtils.notNull(this.getTableConfigsList())){
                for(TableConfig tableConfig:this.getTableConfigsList()){
                    configTable(context,tableConfig);
                }
            }
            Configuration config = new Configuration();
            config.addContext(context);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            myBatisGenerator.generate(new ProgressCallbackImpl());
            return true;
        }catch (Exception e){
            logger.error(" Generate MyBatis File Fail:",e);
            return false;
        }

    }
}
