package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.config.MergeConstants;

import java.util.List;

/**
 * Created by Administrator on 2014/8/11.
 */
public class ForeignKey {

    String foreignTableName ;
    String foreignColumnName ;
    String primaryKeyColumnName;
    String primaryKeyTableName ;
    private IntrospectedTable foreignKeyIntrospectedTable;
    private IntrospectedTable primaryKeyIntrospectedTable;
    private TopLevelClass  foreignKeyTopLevelClass;
    private TopLevelClass  primaryKeyTopLevelClass;

    private Interface foreignDaoMapperInterface;
    private Interface primaryDaoMapperInterface;

    public Interface getForeignDaoMapperInterface() {
        return foreignDaoMapperInterface;
    }

    public void setForeignDaoMapperInterface(Interface foreignDaoMapperInterface) {
        this.foreignDaoMapperInterface = foreignDaoMapperInterface;
    }

    public Interface getPrimaryDaoMapperInterface() {
        return primaryDaoMapperInterface;
    }

    public void setPrimaryDaoMapperInterface(Interface primaryDaoMapperInterface) {
        this.primaryDaoMapperInterface = primaryDaoMapperInterface;
    }

    public String getForeignTableName() {
        return foreignTableName;
    }

    public void setForeignTableName(String foreignTableName) {
        this.foreignTableName = foreignTableName;
    }

    public String getForeignColumnName() {
        return foreignColumnName;
    }

    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
    }

    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
        this.primaryKeyColumnName = primaryKeyColumnName;
    }

    public String getPrimaryKeyTableName() {
        return primaryKeyTableName;
    }

    public void setPrimaryKeyTableName(String primaryKeyTableName) {
        this.primaryKeyTableName = primaryKeyTableName;
    }

    public IntrospectedTable getForeignKeyIntrospectedTable() {
        return foreignKeyIntrospectedTable;
    }

    public void setForeignKeyIntrospectedTable(IntrospectedTable foreignKeyIntrospectedTable) {
        this.foreignKeyIntrospectedTable = foreignKeyIntrospectedTable;

    }

    public IntrospectedTable getPrimaryKeyIntrospectedTable() {
        return primaryKeyIntrospectedTable;
    }

    public void setPrimaryKeyIntrospectedTable(IntrospectedTable primaryKeyIntrospectedTable) {
        this.primaryKeyIntrospectedTable = primaryKeyIntrospectedTable;


    }
    private String getForeignModelTypeName(){
        return this.foreignKeyIntrospectedTable.getBaseRecordType();//从表实体Bean
    }

    private String getFoeignModelPropertyName(){
       String primaryModelShortName = getPrimaryModelShortName();
        return Character.toLowerCase(primaryModelShortName.charAt(0))+primaryModelShortName.substring(1);

    }

    private String getForeignModelShortName (){
        return new FullyQualifiedJavaType(getForeignModelTypeName()).getShortName();
    }

   private String getPrimaryModelPropertyName(){
       String primaryModelShortName =this.getForeignModelShortName();
       return Character.toLowerCase(primaryModelShortName.charAt(0))+primaryModelShortName.substring(1)+"List";
   }

    private String getPrimaryModelTypeName(){
        return this.primaryKeyIntrospectedTable.getBaseRecordType();//从表实体Bean
    }
    private String getPrimaryModelShortName(){
        return new FullyQualifiedJavaType(this.getPrimaryModelTypeName()).getShortName();
    }

    public boolean isOKToGenerateModel(){
        return  this.getPrimaryKeyTopLevelClass()!=null && this.getPrimaryKeyIntrospectedTable() != null && this.getForeignKeyTopLevelClass() != null && this.getForeignKeyIntrospectedTable()!= null;
    }
    public void generateForModel(){

        addProperty(this.getPrimaryKeyTopLevelClass(),this.getPrimaryKeyIntrospectedTable(),this.getPrimaryModelPropertyName(),"java.util.List<"+this.getForeignModelTypeName()+">");

        addProperty(this.getForeignKeyTopLevelClass(),this.getForeignKeyIntrospectedTable(),this.getFoeignModelPropertyName(),this.getPrimaryModelTypeName());


    }
    private String getForeignSelectId(){
        return "selectBy"+this.getPrimaryModelShortName();
    }
    private String getFullForeignSelectId(){
        return this.foreignDaoMapperInterface.getType()+"."+getForeignSelectId();
    }
    private String getPrimarySelectId(){
        return this.getPrimaryDaoMapperInterface().getType()+".selectByPrimaryKey";
    }


    private Document primaryKeyDocument;
    private Document foreignKeyDocument;

    public Document getPrimaryKeyDocument() {
        return primaryKeyDocument;
    }

    public void setPrimaryKeyDocument(Document primaryKeyDocument) {
        this.primaryKeyDocument = primaryKeyDocument;
    }

    public Document getForeignKeyDocument() {
        return foreignKeyDocument;
    }

    public void setForeignKeyDocument(Document foreignKeyDocument) {
        this.foreignKeyDocument = foreignKeyDocument;
    }



    public boolean isOKToGenerateXML(){
       // return AppValidationUtils.notNull(this.foreignTableResultMapXml) &&  AppValidationUtils.notNull(this.primaryTableResultMapXml);
        return this.primaryKeyDocument != null && this.foreignKeyDocument != null;
    }
    public void generateXML(){
        generateForPrimaryXml(this.primaryKeyDocument);
        generateForForeignXml(this.foreignKeyDocument);
    }
    private XmlElement getResultMapXmlElement( XmlElement rootXmlElement){
        List<Element> xmlElementList = rootXmlElement.getElements();
        if(AppValidationUtils.notNull(xmlElementList)){
            for(Element element:xmlElementList){
                String name = ((XmlElement)element).getName();
                if(AppValidationUtils.notNull(name)&& name.equals("resultMap")){
                    return (XmlElement)element;
                }
            }
        }
        return null;
    }

    public void generateForPrimaryXml(Document document){
        XmlElement rootXmlElement = document.getRootElement();
        XmlElement resultMapXmlElement = getResultMapXmlElement(rootXmlElement);
        generateForPrimaryResultMapXml(resultMapXmlElement);

    }
    private XmlElement generateSelect(){
        XmlElement selectElement = new XmlElement("select");
        selectElement.addAttribute(new Attribute("id",this.getForeignSelectId()));

        IntrospectedColumn introspectedColumn = getForeignKeyIntrospectedTable().getColumn(this.getForeignColumnName());
        String foreignKeyColumTypeName = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();
        selectElement.addAttribute(new Attribute("parameterType",foreignKeyColumTypeName));
        selectElement.addAttribute(new Attribute("resultMap","BaseResultMap"));

        selectElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        selectElement.addElement(new TextElement(sb.toString()));
        selectElement.addElement(new TextElement(
                        "  This element is automatically generated by MyBatis Generator, do not modify.")); //$NON-NLS-1$



        selectElement.addElement(new TextElement("-->")); //$NON-NLS-1$

        selectElement.addElement(new TextElement("select"));
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid","Base_Column_List"));
        selectElement.addElement(includeElement);
        selectElement.addElement(new TextElement(" from "
                +getForeignKeyIntrospectedTable().getAliasedFullyQualifiedTableNameAtRuntime()
                +" where "+introspectedColumn.getActualColumnName()+" = "
                +"#{"+introspectedColumn.getJavaProperty()+",jdbcType="+introspectedColumn.getJdbcTypeName()+"}"));
        return selectElement;
    }
    public void generateForForeignXml(Document document){
        XmlElement rootXmlElement = document.getRootElement();
        XmlElement resultMapXmlElement = getResultMapXmlElement(rootXmlElement);
        generateForForeignResultMapXml(resultMapXmlElement);

        XmlElement selectElement = generateSelect();
        rootXmlElement.addElement(selectElement);


    }
    public void generateForPrimaryResultMapXml(XmlElement element){
        XmlElement collectionElement = new XmlElement("collection");
        collectionElement.addAttribute(new Attribute("property", this.getPrimaryModelPropertyName()));
        collectionElement.addAttribute(new Attribute("ofType", this.getForeignModelTypeName()));
        collectionElement.addAttribute(new Attribute("javaType", "ArrayList"));
        collectionElement.addAttribute(new Attribute("column", this.getPrimaryKeyColumnName()));
        collectionElement.addAttribute(new Attribute("select", this.getFullForeignSelectId()));
        element.addElement(collectionElement);
    }
    public void generateForForeignResultMapXml(XmlElement element){
        XmlElement collectionElement = new XmlElement("association");
        collectionElement.addAttribute(new Attribute("property", this.getFoeignModelPropertyName()));
        collectionElement.addAttribute(new Attribute("javaType", this.getPrimaryModelTypeName()));
        collectionElement.addAttribute(new Attribute("column", this.getForeignColumnName()));
        collectionElement.addAttribute(new Attribute("select", this.getPrimarySelectId()));
        element.addElement(collectionElement);
    }
    public TopLevelClass getForeignKeyTopLevelClass() {
        return foreignKeyTopLevelClass;
    }

    public void setForeignKeyTopLevelClass(TopLevelClass foreignKeyTopLevelClass) {
        this.foreignKeyTopLevelClass = foreignKeyTopLevelClass;
    }

    public TopLevelClass getPrimaryKeyTopLevelClass() {
        return primaryKeyTopLevelClass;
    }

    public void setPrimaryKeyTopLevelClass(TopLevelClass primaryKeyTopLevelClass) {
        this.primaryKeyTopLevelClass = primaryKeyTopLevelClass;
    }


    private void addProperty(TopLevelClass topLevelClass,
                             IntrospectedTable introspectedTable, String name,String classType) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType(classType));

        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);

        field.setType(new FullyQualifiedJavaType(classType));
        field.setName(name);

        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(classType), name));
        method.addBodyLine("this." + name + "=" + name + ";");

        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(classType));
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");

        topLevelClass.addMethod(method);
    }
}
