package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.WhenRunner;
import ln.dbx.asiainfo.com.procedure.WhenDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class WhenRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = WhenDocument.When.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        WhenDocument.When when = (  WhenDocument.When)xmlObject;

        WhenRunner whenRunner = new WhenRunner();

        whenRunner.setExpression(when.getExpression());
        whenRunner.setCallProcedureName(when.getCallProcedureName());
        whenRunner.setCallProcedureParams(when.getCallProcedureParams());
        whenRunner.setCallProcedureResultName(when.getCallProcedureResultName());
        whenRunner.setDesc(when.getDesc());
        List<Runner> runnerList = this.parseChildren(when);
        whenRunner.addRunner(runnerList);
        return whenRunner;
    }
}
