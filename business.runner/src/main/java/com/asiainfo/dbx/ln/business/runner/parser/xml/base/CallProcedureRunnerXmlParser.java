package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.CallProcedureRunner;
import ln.dbx.asiainfo.com.procedure.CallProcedureDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class CallProcedureRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = CallProcedureDocument.CallProcedure.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{
        CallProcedureDocument.CallProcedure callProcedureElement = (  CallProcedureDocument.CallProcedure)xmlObject;
        CallProcedureRunner callProcedureRunner = new CallProcedureRunner();
        callProcedureRunner.setDefineName(callProcedureElement.getDefineName());
        callProcedureRunner.setDesc(callProcedureElement.getDesc());
        callProcedureRunner.setRepository(callProcedureElement.getRepository());
        callProcedureRunner.setContainer(callProcedureElement.getContainer());
        callProcedureRunner.setProcedureName(callProcedureElement.getProcedureName());
        callProcedureRunner.setParamsVarName(callProcedureElement.getParamsName());
        return callProcedureRunner;
    }
}
