package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpGetFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpGetFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpGetFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpGetFileDocument.FtpGetFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpGetFileDocument.FtpGetFile ftpGetFile = (FtpGetFileDocument.FtpGetFile)xmlObject;
        FtpGetFileRunner ftpGetFileRunner = new FtpGetFileRunner();
        /*ftpGetFileRunner.setServerId(ftpGetFile.getServerId());
        ftpGetFileRunner.setServerPath(ftpGetFile.getServerPath());
        ftpGetFileRunner.setServerFileName(ftpGetFile.getServerFileName());
        ftpGetFileRunner.setLocalPath(ftpGetFile.getLocalPath());
        ftpGetFileRunner.setLocalFileName(ftpGetFile.getLocalFileName());
        ftpGetFileRunner.setDesc(ftpGetFile.getDesc());*/
        this.parseFtpRunnerXml(ftpGetFileRunner,xmlObject);
        return ftpGetFileRunner;
    }
}
