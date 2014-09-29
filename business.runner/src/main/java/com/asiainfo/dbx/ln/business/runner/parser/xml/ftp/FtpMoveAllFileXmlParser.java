package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpMoveAllFileRunner;
import ln.dbx.asiainfo.com.ftp.FtpMoveAllFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpMoveAllFileXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpMoveAllFileDocument.FtpMoveAllFile.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpMoveAllFileDocument.FtpMoveAllFile ftpMoveAllFile = (FtpMoveAllFileDocument.FtpMoveAllFile)xmlObject;
        FtpMoveAllFileRunner ftpMoveAllFileRunner = new FtpMoveAllFileRunner();
       /* ftpMoveAllFileRunner.setServerId(ftpMoveAllFile.getServerId());


        ftpMoveAllFileRunner.setDesc(ftpMoveAllFile.getDesc());*/
        this.parseFtpRunnerXml(ftpMoveAllFileRunner,xmlObject);
        ftpMoveAllFileRunner.setSrcServerFileNameRegex(ftpMoveAllFile.getSrcServerFileNameRegex());

        return ftpMoveAllFileRunner;
    }
}
