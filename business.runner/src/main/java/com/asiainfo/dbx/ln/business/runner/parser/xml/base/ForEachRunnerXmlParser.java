package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.ForEachRunner;

import ln.dbx.asiainfo.com.procedure.ForEachDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class ForEachRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = ForEachDocument.ForEach.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        ForEachDocument.ForEach ForEach = (  ForEachDocument.ForEach)xmlObject;
        ForEachRunner ForEachRunner = new ForEachRunner();
        ForEachRunner.setCollection(ForEach.getCollection());
        ForEachRunner.setItem(ForEach.getItem());
        ForEachRunner.setDesc(ForEach.getDesc());
        List<Runner> runnerList = this.parseChildren(ForEach);
        ForEachRunner.addRunner(runnerList);
        return ForEachRunner;
    }
}
