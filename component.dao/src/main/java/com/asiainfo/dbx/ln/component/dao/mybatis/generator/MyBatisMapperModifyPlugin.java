package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.component.util.AppFieldUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.List;
import static  com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample.*;

/**
 * Created by yanlei on 2014/8/27.
 */
public class MyBatisMapperModifyPlugin extends PluginAdapter {
    private final Logger logger = LoggerFactory.getLogger(MyBatisMapperModifyPlugin.class);
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        try {

            List<Method> needMethodList = new ArrayList<Method>();
            List<InnerClass> innerClassList = topLevelClass.getInnerClasses();
            for(InnerClass innerClass:innerClassList){
                if(innerClass.getType().getShortName().equals("GeneratedCriteria")){
                    List<Method> methodList = innerClass.getMethods();
                    for(Method method :methodList){
                        if(method.getName().startsWith("and")){
                            needMethodList.add(method);
                        }
                    }
                }
            }
            String topName = topLevelClass.getType().toString();
            String modelName = topName.replace("Example","");
            FullyQualifiedJavaType modelFullName =  new FullyQualifiedJavaType(modelName);

            AppFieldUtils.writeField(topLevelClass, "fields", new ArrayList(), true);
            AppFieldUtils.writeField(topLevelClass, "innerClasses", new ArrayList(), true);
            AppFieldUtils.writeField(topLevelClass, "methods", new ArrayList(), true);
            // public static class CriteriaExt extends   Criteria{
            //protected CriteriaExt(Class classz) {
            //    super(classz);
            //}
            InnerClass innerClass = new InnerClass("CriteriaExt");
            innerClass.setSuperClass(new FullyQualifiedJavaType("Criteria"));
            innerClass.setVisibility(JavaVisibility.PUBLIC);
            innerClass.setStatic(true);


            Method method = new Method("CriteriaExt");
            method.setVisibility(JavaVisibility.PROTECTED);
            method.addBodyLine("super("+modelFullName.getShortName()+".class);");
            method.setConstructor(true);

            innerClass.addMethod(method);
            for(Method needMethod:needMethodList){
                needMethod.setReturnType(new FullyQualifiedJavaType("CriteriaExt"));
                List<String> bodyLineList = needMethod.getBodyLines();
                bodyLineList.remove(bodyLineList.size()-1);
                bodyLineList.add("return  this;");
                innerClass.addMethod(needMethod);
            }



            topLevelClass.addInnerClass(innerClass);
            topLevelClass.setSuperClass(new FullyQualifiedJavaType(MyBatisExample.class.getName()));
            topLevelClass.addImportedType(new FullyQualifiedJavaType(MyBatisExample.class.getName()));

            /**
             public CriteriaExt createCriteriaExt(Class classz) {
                 CriteriaExt criteria = new CriteriaExt(classz);
                 if (oredCriteria.size() == 0) {
                 oredCriteria.add(criteria);
                 }
                 return criteria;
             }
             */
            method = new Method("createCriteriaExt");

            method.setReturnType(new FullyQualifiedJavaType("CriteriaExt"));
            method.setVisibility(JavaVisibility.PUBLIC);


            method.addBodyLine("CriteriaExt criteria = new CriteriaExt();");
            method.addBodyLine("if (oredCriteria.size() == 0) {");
            method.addBodyLine("oredCriteria.add(criteria);");
            method.addBodyLine("}");
            method.addBodyLine("return criteria;");
            topLevelClass.addMethod(method);

            List<IntrospectedColumn> primaryKeyColumnlist  =  introspectedTable.getPrimaryKeyColumns();
            if(primaryKeyColumnlist != null && primaryKeyColumnlist.size()>0) {
                IntrospectedColumn introspectedColumn = primaryKeyColumnlist.get(0);
                method = new Method("andPrimaryKeyEqualTo");
                method.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "primaryKey"));
                method.setReturnType(new FullyQualifiedJavaType("void"));
                method.setVisibility(JavaVisibility.PUBLIC);
                method.addBodyLine(" this.createCriteria().addCriterion(\""+introspectedColumn.getActualColumnName()+" =\", primaryKey, \""+introspectedColumn.getJavaProperty()+"\");");
                topLevelClass.addMethod(method);
            }

        }catch(Exception e){
            logger.error("",e);

        }
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<Method> methodList = interfaze.getMethods();
        String modelName = null;

        for(Method method:methodList){
            if(method.getName().equals("updateByExampleSelective")){
                List<Parameter> parameterList = method.getParameters();
                modelName = parameterList.get(0).getType().getShortName();
            }
            if(method.getName().equals("countByExample")){
                method.setReturnType(new FullyQualifiedJavaType("float"));
            }
            method.getParameters();
            List<Parameter> parameterList = method.getParameters();
            for(int i=0;i<parameterList.size();i++){
                Parameter parameter= parameterList.get(i);
                String parameneterName = parameter.getName();
                if(parameter.getType().getShortName().indexOf("Example") !=-1){
                    Parameter parameter1 = new Parameter(new FullyQualifiedJavaType(MyBatisExample.class.getName()),parameneterName);
                    if(parameter.getAnnotations().size()>0){
                        for(String annotation:parameter.getAnnotations()){
                            parameter1.addAnnotation(annotation);
                        }
                    }
                    parameterList.remove(i);
                    parameterList.add(i,parameter1);
                }
            }

        }
        interfaze.addImportedType(new FullyQualifiedJavaType(MyBatisMapper.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(MyBatisExample.class.getName()));
        interfaze.addSuperInterface(new FullyQualifiedJavaType(MyBatisMapper.class.getName()+"<"+modelName+",MyBatisExample>"));

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    public void modifyParamerType(XmlElement element){
        List<Attribute> attributeList = element.getAttributes();
        for(int i=0;i<attributeList.size();i++){
            Attribute attribute = attributeList.get(i);
            if(attribute.getName().equals("parameterType")){
                Attribute attribute1 = new Attribute("parameterType",MyBatisExample.class.getName());
                attributeList.remove(i);
                attributeList.add(i,attribute1);
            }
        }
    }
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        modifyParamerType(element);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        modifyParamerType(element);
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        modifyParamerType(element);
        return super.sqlMapDeleteByExampleElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        modifyParamerType(element);
        List<Attribute> attributeList = element.getAttributes();
        for(int i=0;i<attributeList.size();i++){
            Attribute attribute = attributeList.get(i);
            if(attribute.getName().equals("resultType")){
                Attribute attribute1 = new Attribute("resultType",Float.class.getName());
                attributeList.remove(i);
                attributeList.add(i,attribute1);
                break;
            }
        }

        List<Element>  elementList = element.getElements();
        List<Element> newElementList = new ArrayList();
        if(elementList != null && elementList.size()>0){
            for(Element element1:elementList) {
                if (element1 instanceof TextElement) {
                    TextElement textElement = (TextElement) element1;
                    if (textElement.getContent().trim().equals("select count(*) from person")) {
                        TextElement selectElement = new TextElement("select");
                        XmlElement ifElement = new XmlElement("if");
                        ifElement.addAttribute(new Attribute("test", "countExpression != null"));
                        ifElement.addElement(new TextElement("${countExpression}"));
                        XmlElement elseElement = new XmlElement("if");
                        elseElement.addAttribute(new Attribute("test", "countExpression == null"));
                        elseElement.addElement(new TextElement("count(*)"));
                        TextElement fromElement = new TextElement("from person");
                        newElementList.add(selectElement);
                        newElementList.add(ifElement);
                        newElementList.add(elseElement);
                        newElementList.add(fromElement);
                        continue;
                    }
                }
                newElementList.add(element1);
            }
        }
        elementList.clear();
        elementList.addAll(newElementList);
        return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
    }

    private XmlElement findChooseElement(XmlElement element){
            List<Element>  xmlElementList = element.getElements();
            if(xmlElementList!= null && xmlElementList.size()>0){
                for(int i=0;i<xmlElementList.size();i++){
                    Element element1 = (Element)xmlElementList.get(i);
                    if(element1 instanceof  XmlElement){
                        XmlElement xmlElement = (XmlElement)element1;
                        if(xmlElement.getName().equals("choose")){
                            return        xmlElement;
                        }else{
                            xmlElement  = findChooseElement(xmlElement);
                            if(xmlElement != null){
                                return xmlElement;
                            }
                        }
                    }

                }
            }
            return null;
    }
    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        /**
         * <when test="criterion.orValue">
         <if test="criterion.value.valid" >
         and
         <trim prefix="(" suffix=")" prefixOverrides="or" >
         <foreach collection="criterion.value.criteria" item="criterion1" separator="or">
         <choose >

         <when test="criterion1.noValue" >

         ${criterion1.condition}
         </when>
         <when test="criterion1.singleValue" >
         ${criterion1.condition} #{criterion1.value}
         </when>
         <when test="criterion1.betweenValue" >
         ${criterion1.condition} #{criterion1.value} and #{criterion1.secondValue}
         </when>
         <when test="criterion1.listValue" >
         ${criterion1.condition}
         <foreach collection="criterion1.value" item="listItem" open="(" close=")" separator="," >
         #{listItem}
         </foreach>
         </when>
         </choose>
         </foreach>
         </trim>
         </if>

         </when>

         */
        XmlElement chooseElement = this.findChooseElement(element);
        XmlElement whenElement = new XmlElement("when");
        chooseElement.addElement(whenElement);
        whenElement.addAttribute(new Attribute("test","criterion.orValue"));
        XmlElement ifElement = new XmlElement("if");
        whenElement.addElement(ifElement);
        ifElement.addAttribute(new Attribute("test","criterion.value.valid"));
        ifElement.addElement(new TextElement(" and "));
        XmlElement trimElement = new XmlElement("trim");
        ifElement.addElement(trimElement);
        trimElement.addAttribute(new Attribute("prefix","("));
        trimElement.addAttribute(new Attribute("suffix",")"));
        trimElement.addAttribute(new Attribute("prefixOverrides","or"));
        XmlElement foreachElement = new XmlElement("foreach");//<foreach collection="criterion.value.criteria" item="criterion1" separator="or">
        trimElement.addElement(foreachElement);
        foreachElement.addAttribute(new Attribute("collection","criterion.value.criteria"));
        foreachElement.addAttribute(new Attribute("item","criterion1"));
        foreachElement.addAttribute(new Attribute("separator","or"));
        chooseElement = new XmlElement("choose");//<choose >
        foreachElement.addElement(chooseElement);

        whenElement = new XmlElement("when");//<when test="criterion1.noValue" >
        whenElement.addAttribute(new Attribute("test","criterion1.noValue"));
        whenElement.addElement(new TextElement("${criterion1.condition}"));//${criterion1.condition}
        chooseElement.addElement(whenElement);



        whenElement = new XmlElement("when");//<when test="criterion1.singleValue" >
        whenElement.addAttribute(new Attribute("test","criterion1.singleValue"));
        whenElement.addElement(new TextElement("${criterion1.condition} #{criterion1.value}"));//${criterion1.condition} #{criterion1.value}
        chooseElement.addElement(whenElement);


        whenElement = new XmlElement("when");//<when test="criterion1.betweenValue" >
        whenElement.addAttribute(new Attribute("test","criterion1.betweenValue"));
        whenElement.addElement(new TextElement("${criterion1.condition} #{criterion1.value} and #{criterion1.secondValue}"));//${criterion1.condition} #{criterion1.value} and #{criterion1.secondValue}
        chooseElement.addElement(whenElement);


        whenElement = new XmlElement("when");//<when test="criterion1.listValue" >
        whenElement.addAttribute(new Attribute("test","criterion1.listValue"));
        whenElement.addElement(new TextElement("${criterion1.condition}"));//${criterion1.condition}
        foreachElement = new XmlElement("foreach");//<foreach collection="criterion1.value" item="listItem" open="(" close=")" separator="," >
        whenElement.addElement(foreachElement);
        foreachElement.addAttribute(new Attribute("collection", "criterion1.value"));
        foreachElement.addAttribute(new Attribute("item","listItem"));
        foreachElement.addAttribute(new Attribute("open","("));
        foreachElement.addAttribute(new Attribute("close",")"));
        foreachElement.addAttribute(new Attribute("separator",","));
        foreachElement.addElement(new TextElement("#{listItem}"));//#{listItem}
        chooseElement.addElement(whenElement);




        return super.sqlMapExampleWhereClauseElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}
