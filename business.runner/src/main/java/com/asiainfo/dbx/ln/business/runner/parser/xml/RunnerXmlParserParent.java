package com.asiainfo.dbx.ln.business.runner.parser.xml;

import com.asiainfo.dbx.ln.business.runner.Runner;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public abstract  class RunnerXmlParserParent implements RunnerXmlParser {
    public abstract  boolean matchXmlObject(XmlObject xmlObject);
    RunnerXmlParser parentParser  = null;

    public RunnerXmlParser getParentParser() {
        return parentParser;
    }

    public void setParentParser(RunnerXmlParser parentParser) {
        this.parentParser = parentParser;
    }
    protected List<Runner> parseChildren(XmlObject xmlObject) throws Exception{
        List<Runner> runnerList = new ArrayList<Runner>();
        XmlCursor xmlCursor = xmlObject.newCursor();
        if(xmlCursor != null){
            int j=0;
            while(xmlCursor.toChild(j)){
                XmlObject xmlSubObject = xmlCursor.getObject();
                if(xmlSubObject != null){
                    Runner runner =  this.getParentParser().parseXmlObject(xmlSubObject);
                    if(runner != null){
                        runnerList.add(runner);
                    }
                }
                j++;
                xmlCursor.toParent();
            }
        }
        return runnerList;

    }


}
