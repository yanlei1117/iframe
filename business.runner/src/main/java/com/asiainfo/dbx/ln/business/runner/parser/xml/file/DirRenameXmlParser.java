package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.DirRenameRunner;
import ln.dbx.asiainfo.com.file.RenameDirDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class DirRenameXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return RenameDirDocument.RenameDir.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DirRenameRunner  dirRenameRunner = new DirRenameRunner();

        this.parseFileRunnerXml(dirRenameRunner, xmlObject);
        return dirRenameRunner;
    }
}
