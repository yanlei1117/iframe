package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.CaseRunner;
import ln.dbx.asiainfo.com.procedure.CaseDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class CaseRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = CaseDocument.Case.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        CaseDocument.Case caseElement = (  CaseDocument.Case)xmlObject;

        CaseRunner caseRunner = new CaseRunner();

        caseRunner.setExecute(caseElement.getExecute());
        caseRunner.setDefineName(caseElement.getDefineName());
        caseRunner.setDesc(caseElement.getDesc());

        List<Runner> runnerList = this.parseChildren(caseElement);
        caseRunner.addRunner(runnerList);
        return caseRunner;
    }
}
