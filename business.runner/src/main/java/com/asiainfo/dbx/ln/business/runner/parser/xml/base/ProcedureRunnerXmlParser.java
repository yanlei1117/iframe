package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.ProcedureRunner;
import ln.dbx.asiainfo.com.procedure.ProcedureDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class ProcedureRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched =   ProcedureDocument.Procedure.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        ProcedureDocument.Procedure procedure = (  ProcedureDocument.Procedure)xmlObject;
        ProcedureRunner procedureRunner = new ProcedureRunner();
        procedureRunner.setName(procedure.getName());
        procedureRunner.setDesc(procedure.getDesc());
        procedureRunner.setParamsMapVarName(procedure.getParamsMapVarName());
        procedureRunner.setParamsMapKeys(procedure.getParamsMapKeys());
        procedureRunner.setReturnVarName(procedure.getReturnVarName());
        procedureRunner.setReturnVarInitVar(procedure.getReturnVarInitVar());
        procedureRunner.setRepository(procedure.getRepository());
        procedureRunner.setCollection(procedure.getContainer());
        List<Runner> runnerList = this.parseChildren(procedure);
        procedureRunner.addRunner(runnerList);
        return procedureRunner;
    }
}
