package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpRunner;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlString;

import javax.xml.namespace.QName;


/**
 * Created by yanlei on 2014/9/23.
 */
public  abstract  class FtpRunnerXmlParser  extends RunnerXmlParserParent {
    public void parseFtpRunnerXml(FtpRunner ftpRunner,XmlObject xmlObject){
        XmlString strXml = (XmlString)xmlObject.selectAttribute(new QName("serverId"));
        String str = null;
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setServerId(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("serverPath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setServerPath(str.trim());
            }
        }

        strXml = (XmlString)xmlObject.selectAttribute(new QName("serverFileNameRegex"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setServerFileNameRegex(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("serverFileName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setServerFileName(str.trim());
            }
        }

        strXml = (XmlString)xmlObject.selectAttribute(new QName("localPath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setLocalPath(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("defineName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setDefineName(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("localFileName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setLocalFileName(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("localFileNameRegex"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setLocalFileNameRegex(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("srcServerPath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setSrcServerPath(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("srcServerFileName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setSrcServerFileName(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("destServerPath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setDestServerPath(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("destServerPath"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setDestServerPath(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("destServerFileName"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setDestServerFileName(str.trim());
            }
        }
        strXml = (XmlString)xmlObject.selectAttribute(new QName("desc"));
        if(strXml != null ) {
            str  = strXml.getStringValue();
            if(str!= null) {
                ftpRunner.setDesc(str.trim());
            }
        }

    }
}
