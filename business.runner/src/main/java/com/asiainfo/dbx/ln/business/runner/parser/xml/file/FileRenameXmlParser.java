package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileRenameRunner;
import ln.dbx.asiainfo.com.file.RenameFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FileRenameXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return RenameFileDocument.RenameFile.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        FileRenameRunner  fileRenameRunner = new FileRenameRunner();

        this.parseFileRunnerXml(fileRenameRunner, xmlObject);
        return fileRenameRunner;
    }
}
