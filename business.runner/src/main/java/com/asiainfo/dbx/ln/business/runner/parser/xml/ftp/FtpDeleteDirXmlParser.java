package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpDeleteDirRunner;
import ln.dbx.asiainfo.com.ftp.FtpDeleteDirDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpDeleteDirXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpDeleteDirDocument.FtpDeleteDir.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpDeleteDirDocument.FtpDeleteDir ftpDeleteDir = (FtpDeleteDirDocument.FtpDeleteDir)xmlObject;
        FtpDeleteDirRunner ftpDeleteDirRunner = new FtpDeleteDirRunner();
      /*  ftpDeleteDirRunner.setServerId(ftpDeleteDir.getServerId());
        ftpDeleteDirRunner.setServerPath(ftpDeleteDir.getServerPath());
        ftpDeleteDirRunner.setDesc(ftpDeleteDir.getDesc());*/
        this.parseFtpRunnerXml(ftpDeleteDirRunner,xmlObject);
        return ftpDeleteDirRunner;
    }
}
