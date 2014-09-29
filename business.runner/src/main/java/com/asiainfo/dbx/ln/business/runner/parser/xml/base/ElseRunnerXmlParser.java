package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.ElseRunner;
import ln.dbx.asiainfo.com.procedure.ElseDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class ElseRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = ElseDocument.Else.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        ElseDocument.Else elseElement = (  ElseDocument.Else)xmlObject;

        ElseRunner elseRunner = new ElseRunner();

        elseRunner.setExpression(elseElement.getExpression());
        elseRunner.setCallProcedureName(elseElement.getCallProcedureName());
        elseRunner.setCallProcedureParams(elseElement.getCallProcedureParams());
        elseRunner.setCallProcedureResultName(elseElement.getCallProcedureResultName());
        elseRunner.setDesc(elseElement.getDesc());
        List<Runner> runnerList = this.parseChildren(elseElement);
        elseRunner.addRunner(runnerList);
        return elseRunner;
    }
}
