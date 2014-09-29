package com.asiainfo.dbx.ln.business.runner.parser.xml.das;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.das.DasUpdateRunner;
import ln.dbx.asiainfo.com.das.DasUpdateDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasUpdateRunnerXmlParser extends RunnerXmlParserParent {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched =  DasUpdateDocument.DasUpdate.class.isInstance(xmlObject);
        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DasUpdateDocument.DasUpdate dasUpdate = (DasUpdateDocument.DasUpdate)xmlObject;
        DasUpdateRunner dasUpdateRunner = new DasUpdateRunner();
        dasUpdateRunner.setDesc(dasUpdate.getDesc());
        dasUpdateRunner.setRepository(dasUpdate.getRepository());
        dasUpdateRunner.setContainer(dasUpdate.getContainer());
        dasUpdateRunner.setCollection(dasUpdate.getCollection());
        dasUpdateRunner.setItemId(dasUpdate.getItemId());

        return dasUpdateRunner;
    }

}
