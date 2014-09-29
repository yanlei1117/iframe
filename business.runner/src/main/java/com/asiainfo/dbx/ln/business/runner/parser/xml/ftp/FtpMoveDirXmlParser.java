package com.asiainfo.dbx.ln.business.runner.parser.xml.ftp;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.ftp.FtpMoveDirRunner;
import ln.dbx.asiainfo.com.ftp.FtpMoveDirDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FtpMoveDirXmlParser extends FtpRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return FtpMoveDirDocument.FtpMoveDir.class.isInstance(xmlObject);
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {
        FtpMoveDirDocument.FtpMoveDir ftpMoveDir = (FtpMoveDirDocument.FtpMoveDir)xmlObject;
        FtpMoveDirRunner ftpMoveDirRunner = new FtpMoveDirRunner();
      /*  ftpMoveDirRunner.setServerId(ftpMoveDir.getServerId());


        ftpMoveDirRunner.setDesc(ftpMoveDir.getDesc());*/
        this.parseFtpRunnerXml(ftpMoveDirRunner,xmlObject);
        ftpMoveDirRunner.setSrcServerPath(ftpMoveDir.getSrcServerPath());

        ftpMoveDirRunner.setDestServerPath(ftpMoveDir.getDestServerPath());
        return ftpMoveDirRunner;
    }
}
