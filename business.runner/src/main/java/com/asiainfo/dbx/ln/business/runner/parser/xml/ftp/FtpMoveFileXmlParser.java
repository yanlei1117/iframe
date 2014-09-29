package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpMoveFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpMoveFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpMoveFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpMoveFileDocument.FtpMoveFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpMoveFileDocument.FtpMoveFile ftpMoveFile = (FtpMoveFileDocument.FtpMoveFile)xmlObject;
        FtpMoveFileRunner ftpMoveFileRunner = new FtpMoveFileRunner();
     /*   ftpMoveFileRunner.setServerId(ftpMoveFile.getServerId());
        ftpMoveFileRunner.setSrcServerPath(ftpMoveFile.getSrcServerPath());
        ftpMoveFileRunner.setSrcServerFileName(ftpMoveFile.getSrcServerFileName());
        ftpMoveFileRunner.setDestServerPath(ftpMoveFile.getDestServerPath());
        ftpMoveFileRunner.setDestServerFileName(ftpMoveFile.getDestServerFileName());
        ftpMoveFileRunner.setDesc(ftpMoveFile.getDesc());*/
        this.parseFtpRunnerXml(ftpMoveFileRunner,xmlObject);
        return ftpMoveFileRunner;
    }
}
