package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Created by yanlei on 2014/8/1.
 */
public class PaginationPlugin extends PluginAdapter {
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addProperty(topLevelClass, introspectedTable, "offset");
        addProperty(topLevelClass, introspectedTable, "limit");

        return super.modelExampleClassGenerated(topLevelClass,
                introspectedTable);
    }


    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        String driverClass =  introspectedTable.getContext().getJdbcConnectionConfiguration().getDriverClass();


        if(driverClass!= null && driverClass.indexOf("mysql") !=-1) {//mysql

            XmlElement paginationElement = new XmlElement("if");
            paginationElement.addAttribute(new Attribute("test", "offset != null and offset>=0"));
            paginationElement.addElement(new TextElement("limit #{offset} , #{limit}"));

            element.addElement(paginationElement);

        }else if(driverClass!= null && driverClass.indexOf("oracle") !=-1){//oracle
            XmlElement paginationElementStart = new XmlElement("if");
            paginationElementStart.addAttribute(new Attribute("test", "offset != null and offset>=0"));
            paginationElementStart.addElement(new TextElement("select * from ( select temp_.*, rownum row_id from ( "));
            element.addElement(0,paginationElementStart);

            XmlElement paginationElementEnd = new XmlElement("if");
            paginationElementEnd.addAttribute(new Attribute("test", "offset != null and offset>=0"));
            paginationElementEnd.addElement(new TextElement(")temp_ where rownum <= #{offset+limit} ) where row_id > #{offset}"));
            element.addElement(paginationElementStart);

        }
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,
                introspectedTable);
    }
    private void addProperty(TopLevelClass topLevelClass,
                          IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);

        field.setType(PrimitiveTypeWrapper.getIntegerInstance());
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }
    /**
     * This plugin is always valid - no properties are required
     */
    public boolean validate(List<String> warnings) {
        return true;
    }



}
