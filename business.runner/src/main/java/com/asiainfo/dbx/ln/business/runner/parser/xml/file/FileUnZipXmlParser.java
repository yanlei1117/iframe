package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.FileUnZipRunner;
import ln.dbx.asiainfo.com.file.UnZipFileDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class FileUnZipXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return UnZipFileDocument.UnZipFile.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        FileUnZipRunner  fileUnZipRunner = new FileUnZipRunner();

        this.parseFileRunnerXml(fileUnZipRunner, xmlObject);
        return fileUnZipRunner;
    }
}
