package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.DirZipRunner;
import ln.dbx.asiainfo.com.file.ZipDirDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class DirZipXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return ZipDirDocument.ZipDir.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DirZipRunner  dirZipRunner = new DirZipRunner();

        this.parseFileRunnerXml(dirZipRunner, xmlObject);
        return dirZipRunner;
    }
}
