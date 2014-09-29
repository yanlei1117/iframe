package com.asiainfo.dbx.ln.business.runner.parser.xml.das;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.das.DasCountRunner;
import ln.dbx.asiainfo.com.das.DasCountDocument;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DasCountRunnerXmlParser extends RunnerXmlParserParent {
    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched =  DasCountDocument.DasCount.class.isInstance(xmlObject);
        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception {

        DasCountDocument.DasCount dasCount = (DasCountDocument.DasCount)xmlObject;
        DasCountRunner dasCountRunner = new DasCountRunner();
        dasCountRunner.setDesc(dasCount.getDesc());
        dasCountRunner.setRepository(dasCount.getRepository());
        dasCountRunner.setContainer(dasCount.getContainer());
        dasCountRunner.setCollection(dasCount.getCollection());
        dasCountRunner.setCriteria(dasCount.getCriteria());
        dasCountRunner.setDefineName(dasCount.getDefineName());
        return dasCountRunner;
    }

}
