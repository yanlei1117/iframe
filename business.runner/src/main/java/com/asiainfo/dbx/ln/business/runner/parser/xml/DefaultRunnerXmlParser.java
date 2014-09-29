package com.asiainfo.dbx.ln.business.runner.parser.xml;

import com.asiainfo.dbx.ln.business.runner.Runner;
import org.apache.xmlbeans.XmlObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/9/19.
 */
public  class DefaultRunnerXmlParser implements RunnerXmlParser {
    List<RunnerXmlParserParent> xmlParserList = new ArrayList<RunnerXmlParserParent>();

    public List<RunnerXmlParserParent> getXmlParserList() {
        return xmlParserList;
    }

    public void setXmlParserList(List<RunnerXmlParserParent> xmlParserList) {
        if(this.xmlParserList != null){
            this.xmlParserList.addAll(xmlParserList);
            for(RunnerXmlParserParent xmlParser:this.xmlParserList){
                xmlParser.setParentParser(this);
            }
        }
    }



    @Override
    public Runner parseXmlObject(XmlObject xmlObject)  throws Exception{
        for(RunnerXmlParserParent xmlParser:this.xmlParserList){
           if(xmlParser.matchXmlObject(xmlObject)){
               return xmlParser.parseXmlObject(xmlObject);
           }
        }
        return null;
    }
}
