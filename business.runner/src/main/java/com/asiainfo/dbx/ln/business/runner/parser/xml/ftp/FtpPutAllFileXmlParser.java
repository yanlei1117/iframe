package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpPutAllFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpPutAllFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpPutAllFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpPutAllFileDocument.FtpPutAllFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpPutAllFileDocument.FtpPutAllFile ftpPutAllFile = (FtpPutAllFileDocument.FtpPutAllFile)xmlObject;
        FtpPutAllFileRunner ftpPutAllFileRunner = new FtpPutAllFileRunner();
   /*     ftpPutAllFileRunner.setServerId(ftpPutAllFile.getServerId());
        ftpPutAllFileRunner.setServerPath(ftpPutAllFile.getServerPath());
        ftpPutAllFileRunner.setLocalPath(ftpPutAllFile.getLocalPath());
        ftpPutAllFileRunner.setLocalFileNameRegex(ftpPutAllFile.getLocalFileNameRegex());
        ftpPutAllFileRunner.setDesc(ftpPutAllFile.getDesc());*/
        this.parseFtpRunnerXml(ftpPutAllFileRunner,xmlObject);
        return ftpPutAllFileRunner;
    }
}
