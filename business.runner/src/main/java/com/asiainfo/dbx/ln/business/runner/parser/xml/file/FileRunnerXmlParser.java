package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileRunner;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlString;

import javax.xml.namespace.QName;


/**
 * Created by yanlei on 2014/9/23.
 */
public  abstract  class FileRunnerXmlParser extends RunnerXmlParserParent {
    public void parseFileRunnerXml(FileRunner fileRunner,XmlObject xmlObject){
        XmlString strXml = (XmlString)xmlObject.selectAttribute(new QName("localPath"));
        String str = null;
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setLocalPath(str.trim());
            }
        }

        strXml = (XmlString)xmlObject.selectAttribute(new QName("localFileNameRegex"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setLocalFileNameRegex(str.trim());
            }
        }

        strXml = (XmlString)xmlObject.selectAttribute(new QName("defineName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setDefineName(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("destFileNameRegex"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setDestFileNameRegex(str.trim());
            }
        }

        strXml = (XmlString)xmlObject.selectAttribute(new QName("destLocalPath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setDestLocalPath(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("zipFilePath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setZipFilePath(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("zipFileName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setZipFileName(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("destDirName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                fileRunner.setDestDirName(str.trim());
            }
        }
    }
}
