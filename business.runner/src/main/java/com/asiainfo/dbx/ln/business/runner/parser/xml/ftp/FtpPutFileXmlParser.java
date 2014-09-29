package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpPutFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpPutFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpPutFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpPutFileDocument.FtpPutFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpPutFileDocument.FtpPutFile ftpPutFile = (FtpPutFileDocument.FtpPutFile)xmlObject;
        FtpPutFileRunner ftpPutFileRunner = new FtpPutFileRunner();
       /* ftpPutFileRunner.setServerId(ftpPutFile.getServerId());
        ftpPutFileRunner.setServerPath(ftpPutFile.getServerPath());
        ftpPutFileRunner.setServerFileName(ftpPutFile.getServerFileName());
        ftpPutFileRunner.setLocalPath(ftpPutFile.getLocalPath());
        ftpPutFileRunner.setLocalFileName(ftpPutFile.getLocalFileName());
        ftpPutFileRunner.setDesc(ftpPutFile.getDesc());*/
        this.parseFtpRunnerXml(ftpPutFileRunner,xmlObject);
        return ftpPutFileRunner;
    }
}
