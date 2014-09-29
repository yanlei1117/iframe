package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/7/30.
 */

public class TestMyBatisFileGenerator {
/*
    @Test
    public void testMyBatisFileGeneratorByXml(){
        MyBatisFileGeneratorByXml myBatisFileGeneratorByXml = new MyBatisFileGeneratorByXml();
        myBatisFileGeneratorByXml.setGeneratorXml("classpath:config/mybatis/mybatis_generator.xml");
        boolean result = myBatisFileGeneratorByXml.generate();
        Assert.assertThat(result, IsEqual.equalTo(true));
    }*/

    @Test
    public void testMyBatisFileGeneratorBySetter(){
        MyBatisFileGeneratorBySetter myBatisFileGenerator = new MyBatisFileGeneratorBySetter();
        myBatisFileGenerator.setConnectionURL("jdbc:mysql://localhost:3306/workflow?useUnicode=true&amp;characterEncoding=UTF-8");
        myBatisFileGenerator.setDriverClass("com.mysql.jdbc.Driver");
        myBatisFileGenerator.setUserId("root");
        myBatisFileGenerator.setPassword("yanlei");
        myBatisFileGenerator.setTargetDir(new File(".").getAbsolutePath()+"\\component.dao\\src\\main\\java");
        myBatisFileGenerator.setRepository("mysql108");
        myBatisFileGenerator.setContainer("component");
        List<TableConfig> tableConfigList = new ArrayList<TableConfig>();
        TableConfig tableConfig = new TableConfig();
        tableConfig.setJavaPojoName("Person");
        tableConfig.setTableName("person");
        tableConfigList.add(tableConfig);
         tableConfig = new TableConfig();
        tableConfig.setJavaPojoName("StudyExperience");
        tableConfig.setTableName("study_experience");
        tableConfigList.add(tableConfig);

        myBatisFileGenerator.setTableConfigsList(tableConfigList);
        boolean result = myBatisFileGenerator.generate();
        Assert.assertThat(result, IsEqual.equalTo(true));
    }


}
