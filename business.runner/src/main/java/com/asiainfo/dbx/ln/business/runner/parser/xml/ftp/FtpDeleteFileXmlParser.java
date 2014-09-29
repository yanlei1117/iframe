package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpDeleteFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpDeleteFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpDeleteFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpDeleteFileDocument.FtpDeleteFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpDeleteFileDocument.FtpDeleteFile ftpDeleteFile = (FtpDeleteFileDocument.FtpDeleteFile)xmlObject;
        FtpDeleteFileRunner ftpDeleteFileRunner = new FtpDeleteFileRunner();
       /* ftpDeleteFileRunner.setServerId(ftpDeleteFile.getServerId());
        ftpDeleteFileRunner.setServerPath(ftpDeleteFile.getServerPath());
        ftpDeleteFileRunner.setServerFileName(ftpDeleteFile.getServerfileName());
        ftpDeleteFileRunner.setDesc(ftpDeleteFile.getDesc());*/
        this.parseFtpRunnerXml(ftpDeleteFileRunner,xmlObject);
        return ftpDeleteFileRunner;
    }
}
