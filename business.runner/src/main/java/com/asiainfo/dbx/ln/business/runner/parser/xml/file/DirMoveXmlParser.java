package com.asiainfo.dbx.ln.business.runner.parser.xml.file;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.file.DirMoveRunner;
import ln.dbx.asiainfo.com.file.MoveDirDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/20.
 */
public class DirMoveXmlParser extends FileRunnerXmlParser {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        return MoveDirDocument.MoveDir.class.isInstance(xmlObject);

    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DirMoveRunner dirMoveRunner = new DirMoveRunner();

        this.parseFileRunnerXml(dirMoveRunner, xmlObject);
        return dirMoveRunner;
    }
}
