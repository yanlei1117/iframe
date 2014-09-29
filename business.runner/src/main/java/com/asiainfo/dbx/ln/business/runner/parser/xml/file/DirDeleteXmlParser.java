package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.DirDeleteRunner;
import ln.dbx.asiainfo.com.file.DeleteDirDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class DirDeleteXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return DeleteDirDocument.DeleteDir.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DirDeleteRunner dirDeleteRunner = new DirDeleteRunner();

        this.parseFileRunnerXml(dirDeleteRunner, xmlObject);
        return dirDeleteRunner;
    }
}
