package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpRenameFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpRenameFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpRenameFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpRenameFileDocument.FtpRenameFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpRenameFileDocument.FtpRenameFile ftpRenameFile = (FtpRenameFileDocument.FtpRenameFile)xmlObject;
        FtpRenameFileRunner ftpRenameFileRunner = new FtpRenameFileRunner();
       /* ftpRenameFileRunner.setServerId(ftpRenameFile.getServerId());
        ftpRenameFileRunner.setServerPath(ftpRenameFile.getServerPath());


        ftpRenameFileRunner.setDesc(ftpRenameFile.getDesc());*/
        this.parseFtpRunnerXml(ftpRenameFileRunner,xmlObject);
        ftpRenameFileRunner.setSrcFileName(ftpRenameFile.getSrcFileName());
        ftpRenameFileRunner.setDestFileName(ftpRenameFile.getDestFileName());
        return ftpRenameFileRunner;
    }
}
