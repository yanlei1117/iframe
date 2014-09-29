package com.asiainfo.dbx.ln.business.runner.parser.xml.das;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.das.DasDeleteRunner;
import ln.dbx.asiainfo.com.das.DasDeleteDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasDeleteRunnerXmlParser extends RunnerXmlParserParent {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched =  DasDeleteDocument.DasDelete.class.isInstance(xmlObject);
        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DasDeleteDocument.DasDelete dasDelete = (DasDeleteDocument.DasDelete)xmlObject;
        DasDeleteRunner dasDeleteRunner = new DasDeleteRunner();
        dasDeleteRunner.setDesc(dasDelete.getDesc());
        dasDeleteRunner.setRepository(dasDelete.getRepository());
        dasDeleteRunner.setContainer(dasDelete.getContainer());
        dasDeleteRunner.setCollection(dasDelete.getCollection());
        dasDeleteRunner.setItemId(dasDelete.getItemId());
        return dasDeleteRunner;
    }

}
