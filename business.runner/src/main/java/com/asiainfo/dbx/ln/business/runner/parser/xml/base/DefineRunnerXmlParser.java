package com.asiainfo.dbx.ln.business.runner.parser.xml.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParserParent;
import com.asiainfo.dbx.ln.business.runner.runner.base.DefineRunner;

import ln.dbx.asiainfo.com.procedure.DefineDocument;
import org.apache.xmlbeans.XmlObject;

import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public class DefineRunnerXmlParser extends RunnerXmlParserParent {

    @Override
    public boolean matchXmlObject(XmlObject xmlObject) {
        boolean matched = DefineDocument.Define.class.isInstance(xmlObject);

        return matched;
    }

    @Override
    public Runner parseXmlObject(XmlObject xmlObject) throws Exception{

        DefineDocument.Define Define = (  DefineDocument.Define)xmlObject;
        DefineRunner DefineRunner = new DefineRunner();
        DefineRunner.setVarName(Define.getVarName());
        DefineRunner.setVarValue(Define.getVarValue());
        DefineRunner.setDesc(Define.getDesc());
        List<Runner> runnerList = this.parseChildren(Define);
        DefineRunner.addRunner(runnerList);
        return DefineRunner;
    }
}
