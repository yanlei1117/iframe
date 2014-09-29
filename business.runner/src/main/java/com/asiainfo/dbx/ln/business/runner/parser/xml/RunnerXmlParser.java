package com.asiainfo.dbx.ln.business.runner.parser.xml;

import com.asiainfo.dbx.ln.business.runner.Runner;
import org.apache.xmlbeans.XmlObject;

/**
 * Created by yanlei on 2014/9/19.
 */
public interface RunnerXmlParser {

    public Runner parseXmlObject(XmlObject xmlObject) throws Exception;
}
