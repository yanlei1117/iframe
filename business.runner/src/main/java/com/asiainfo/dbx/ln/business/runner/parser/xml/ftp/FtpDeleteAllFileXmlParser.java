package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpDeleteAllFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpDeleteAllFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpDeleteAllFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpDeleteAllFileDocument.FtpDeleteAllFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpDeleteAllFileDocument.FtpDeleteAllFile ftpDeleteAllFile = (FtpDeleteAllFileDocument.FtpDeleteAllFile)xmlObject;
        FtpDeleteAllFileRunner ftpDeleteAllFileRunner = new FtpDeleteAllFileRunner();
        /*ftpDeleteAllFileRunner.setServerId(ftpDeleteAllFile.getServerId());
        ftpDeleteAllFileRunner.setServerPath(ftpDeleteAllFile.getServerPath());
        ftpDeleteAllFileRunner.setServerFileNameRegex(ftpDeleteAllFile.getServerFileNameRegex());
        ftpDeleteAllFileRunner.setDesc(ftpDeleteAllFile.getDesc());*/
        this.parseFtpRunnerXml(ftpDeleteAllFileRunner,xmlObject);
        return ftpDeleteAllFileRunner;
    }
}
