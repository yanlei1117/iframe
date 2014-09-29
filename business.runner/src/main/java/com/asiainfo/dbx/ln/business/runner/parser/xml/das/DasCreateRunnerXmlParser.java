package com.asiainfo.dbx.ln.business.runner.parser.xml.das;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.runner.das.DasCreateRunner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import ln.dbx.asiainfo.com.das.DasCreateDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasCreateRunnerXmlParser extends RunnerXmlParserParent {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched =  DasCreateDocument.DasCreate.class.isInstance(xmlObject);
        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DasCreateDocument.DasCreate dasCreate = (DasCreateDocument.DasCreate)xmlObject;
        DasCreateRunner dasCreateRunner = new DasCreateRunner();
        dasCreateRunner.setDesc(dasCreate.getDesc());
        dasCreateRunner.setRepository(dasCreate.getRepository());
        dasCreateRunner.setContainer(dasCreate.getContainer());
        dasCreateRunner.setCollection(dasCreate.getCollection());
        dasCreateRunner.setItem(dasCreate.getItem());
        return dasCreateRunner;
    }

}
