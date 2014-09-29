package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpListFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpListFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpListFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpListFileDocument.FtpListFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpListFileDocument.FtpListFile ftpListFile = (FtpListFileDocument.FtpListFile)xmlObject;
        FtpListFileRunner ftpListFileRunner = new FtpListFileRunner();
        /*ftpListFileRunner.setServerId(ftpListFile.getServerId());
        ftpListFileRunner.setServerPath(ftpListFile.getServerPath());
        ftpListFileRunner.setServerFileNameRegex(ftpListFile.getServerFileNameRegex());
        ftpListFileRunner.setDesc(ftpListFile.getDesc());*/
        this.parseFtpRunnerXml(ftpListFileRunner,xmlObject);
        return ftpListFileRunner;
    }
}
