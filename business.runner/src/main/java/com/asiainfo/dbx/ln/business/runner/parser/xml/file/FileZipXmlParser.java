package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileZipRunner;
import ln.dbx.asiainfo.com.file.ZipFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FileZipXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return ZipFileDocument.ZipFile.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        FileZipRunner  fileZipRunner = new FileZipRunner();

        this.parseFileRunnerXml(fileZipRunner, xmlObject);
        return fileZipRunner;
    }
}
