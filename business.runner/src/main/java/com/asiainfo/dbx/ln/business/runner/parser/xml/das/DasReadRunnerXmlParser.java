package com.asiainfo.dbx.ln.business.runner.parser.xml.das;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.das.DasReadRunner;
import ln.dbx.asiainfo.com.das.DasReadDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasReadRunnerXmlParser extends RunnerXmlParserParent {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched =  DasReadDocument.DasRead.class.isInstance(xmlObject);
        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DasReadDocument.DasRead dasRead = (DasReadDocument.DasRead)xmlObject;
        DasReadRunner dasReadRunner = new DasReadRunner();
        dasReadRunner.setDesc(dasRead.getDesc());
        dasReadRunner.setRepository(dasRead.getRepository());
        dasReadRunner.setContainer(dasRead.getContainer());
        dasReadRunner.setCollection(dasRead.getCollection());
        dasReadRunner.setItemId(dasRead.getItemId());
        dasReadRunner.setCriteria(dasRead.getCriteria());
        dasReadRunner.setDefineName(dasRead.getDefineName());
        return dasReadRunner;
    }

}
