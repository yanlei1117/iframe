package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpRenameAllFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpRenameAllFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpRenameAllFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpRenameAllFileDocument.FtpRenameAllFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpRenameAllFileDocument.FtpRenameAllFile ftpRenameAllFile = (FtpRenameAllFileDocument.FtpRenameAllFile)xmlObject;
        FtpRenameAllFileRunner ftpRenameAllFileRunner = new FtpRenameAllFileRunner();
     /*   ftpRenameAllFileRunner.setServerId(ftpRenameAllFile.getServerId());
        ftpRenameAllFileRunner.setServerPath(ftpRenameAllFile.getServerPath());
        ftpRenameAllFileRunner.setDesc(ftpRenameAllFile.getDesc());*/
        this.parseFtpRunnerXml(ftpRenameAllFileRunner,xmlObject);
        ftpRenameAllFileRunner.setSrcFileNameRegex(ftpRenameAllFile.getSrcFileNameRegex());
        ftpRenameAllFileRunner.setDestFileNameRegex(ftpRenameAllFile.getDestFileNameRegex());

        return ftpRenameAllFileRunner;
    }
}
