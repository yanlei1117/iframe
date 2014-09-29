package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpGetAllFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpGetAllFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpGetAllFileXmlParser extends FtpRunnerXmlParser{
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpGetAllFileDocument.FtpGetAllFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        //FtpGetAllFileDocument.FtpGetAllFile ftpGetAllFileXml = (FtpGetAllFileDocument.FtpGetAllFile)xmlObject;
        FtpGetAllFileRunner ftpGetAllFileRunner = new FtpGetAllFileRunner();
       /* ftpGetAllFileRunner.setServerId(ftpGetAllFile.getServerId());
        ftpGetAllFileRunner.setServerPath(ftpGetAllFile.getServerPath());
        ftpGetAllFileRunner.setLocalPath(ftpGetAllFile.getLocalPath());
        ftpGetAllFileRunner.setDesc(ftpGetAllFile.getDesc());*/
        this.parseFtpRunnerXml(ftpGetAllFileRunner,xmlObject);
        return ftpGetAllFileRunner;
    }
}
