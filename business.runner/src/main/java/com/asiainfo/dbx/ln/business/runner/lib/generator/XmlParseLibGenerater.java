package com.asiainfo.dbx.ln.business.runner.lib.generator;

import com.asiainfo.dbx.ln.component.util.AppResourceUtils;
import org.apache.xmlbeans.impl.tool.SchemaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Created by yanlei on 2014/9/19.
 */
public class XmlParseLibGenerater {
    private static final Logger logger = LoggerFactory.getLogger(XmlParseLibGenerater.class);
    public void generate(String srcXsdClassPath,String jarName) throws Exception{
        logger.info("generate parse lib named '{}' for xsd '{}'",jarName,srcXsdClassPath);
        Resource resource = AppResourceUtils.getResource(srcXsdClassPath);
        SchemaCompiler schemaCompiler = new SchemaCompiler();
        String xsdFilePath = resource.getFile().getAbsolutePath();

        String destDir = null;
        int runnerIndex  = xsdFilePath.indexOf("business.runner");
        int libIndex  = xsdFilePath.indexOf("lib");
        if(runnerIndex != -1){
            destDir = xsdFilePath.substring(0,runnerIndex)+"lib_dependency";
        }else if(libIndex!=-1){
            destDir = xsdFilePath.substring(0,runnerIndex+4);
        }
        File srcDir = new File(destDir,"src");
        if(!srcDir.exists()){
            srcDir.mkdir();
        }
        File classesDir = new File(destDir,"classes");
        if(!classesDir.exists()){
            classesDir.mkdir();
        }
        clearDir(srcDir);
        clearDir(classesDir);
        SchemaCompiler.Parameters  parameters = new SchemaCompiler.Parameters();
        parameters.setOutputJar(new File(destDir,jarName));
        parameters.setXsdFiles(new File []{resource.getFile()});
        parameters.setSrcDir(srcDir);
        parameters.setClassesDir(classesDir);
        schemaCompiler.compile(parameters);
        //schemaCompiler.main(new String []{"-out",new File(destDir,jarName).getAbsolutePath(),xsdFilePath});
        deleteFile(srcDir);
        deleteFile(classesDir);
        logger.info("generate parse lib named '{}' for xsd '{}' finish",new File(destDir,jarName).getAbsolutePath(),xsdFilePath);
    }
    public void clearDir(File file){
        if(file.isDirectory()){
            File [] files = file.listFiles();
            for(File subfile :files){
                deleteFile(subfile);
            }
        }
    }
    public void deleteFile(File file){
        if(file.isDirectory()){
            File [] files = file.listFiles();
            for(File subfile :files){
                deleteFile(subfile);
            }
        }
        file.delete();
    }
    public void generateProcedureParseLib() throws Exception{
        this.generate("/runner/schema/ProcedureRunner.xsd","ProcedureXmlParse.jar");
    }
    public void generateDasParseLib() throws Exception{
        this.generate("/runner/schema/DasRunner.xsd","DasXmlParse.jar");
    }
    public void generateFtpParseLib() throws Exception{
        this.generate("/runner/schema/FtpRunner.xsd","FtpXmlParse.jar");
    }
    public void generateFileParseLib() throws Exception{
        this.generate("/runner/schema/FileRunner.xsd","FileXmlParse.jar");
    }
    public void generateAllParseLib() throws Exception {
        generateProcedureParseLib();
        generateDasParseLib();
        generateFtpParseLib();
        generateFileParseLib();
    }
    public static void main(String args []){
        try {
            XmlParseLibGenerater xmlParseLibGenerater =  new XmlParseLibGenerater();
            xmlParseLibGenerater.generateAllParseLib();
        }catch(Exception e){
            logger.error("",e);
        }

    }
}
