package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.LogicFalseRunner;
import ln.dbx.asiainfo.com.procedure.LogicFalseDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class LogicFalseRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = LogicFalseDocument.LogicFalse.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        LogicFalseDocument.LogicFalse LogicFalse = (  LogicFalseDocument.LogicFalse)xmlObject;

        LogicFalseRunner LogicFalseRunner = new LogicFalseRunner();

        LogicFalseRunner.setExpression(LogicFalse.getExpression());
        LogicFalseRunner.setCallProcedureName(LogicFalse.getCallProcedureName());
        LogicFalseRunner.setCallProcedureParams(LogicFalse.getCallProcedureParams());
        LogicFalseRunner.setCallProcedureResultName(LogicFalse.getCallProcedureResultName());
        LogicFalseRunner.setDesc(LogicFalse.getDesc());
        List<Runner> runnerList = this.parseChildren(LogicFalse);
        LogicFalseRunner.addRunner(runnerList);
        return LogicFalseRunner;
    }
}
