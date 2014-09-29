package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileMoveRunner;
import ln.dbx.asiainfo.com.file.MoveFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FileMoveXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return MoveFileDocument.MoveFile.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        FileMoveRunner  fileMoveRunner = new FileMoveRunner();

        this.parseFileRunnerXml(fileMoveRunner, xmlObject);
        return fileMoveRunner;
    }
}
