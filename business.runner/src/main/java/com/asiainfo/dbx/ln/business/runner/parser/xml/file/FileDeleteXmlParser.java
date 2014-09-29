package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileDeleteRunner;
import ln.dbx.asiainfo.com.file.DeleteFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FileDeleteXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return DeleteFileDocument.DeleteFile.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        FileDeleteRunner  fileDeleteRunner = new FileDeleteRunner();

        this.parseFileRunnerXml(fileDeleteRunner, xmlObject);
        return fileDeleteRunner;
    }
}
