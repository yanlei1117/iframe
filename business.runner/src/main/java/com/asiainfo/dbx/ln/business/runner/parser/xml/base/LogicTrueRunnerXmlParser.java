package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.LogicTrueRunner;
import ln.dbx.asiainfo.com.procedure.LogicTrueDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class LogicTrueRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = LogicTrueDocument.LogicTrue.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        LogicTrueDocument.LogicTrue LogicTrue = (  LogicTrueDocument.LogicTrue)xmlObject;

        LogicTrueRunner LogicTrueRunner = new LogicTrueRunner();

        LogicTrueRunner.setExpression(LogicTrue.getExpression());
        LogicTrueRunner.setCallProcedureName(LogicTrue.getCallProcedureName());
        LogicTrueRunner.setCallProcedureParams(LogicTrue.getCallProcedureParams());
        LogicTrueRunner.setCallProcedureResultName(LogicTrue.getCallProcedureResultName());
        LogicTrueRunner.setDesc(LogicTrue.getDesc());
        List<Runner> runnerList = this.parseChildren(LogicTrue);
        LogicTrueRunner.addRunner(runnerList);
        return LogicTrueRunner;
    }
}
