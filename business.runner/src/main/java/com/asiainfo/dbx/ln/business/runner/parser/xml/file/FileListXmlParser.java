package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileListRunner;
import ln.dbx.asiainfo.com.file.ListFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FileListXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return ListFileDocument.ListFile.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        FileListRunner  fileListRunner = new FileListRunner();

        this.parseFileRunnerXml(fileListRunner, xmlObject);
        return fileListRunner;
    }
}
