package com.asiainfo.dbx.ln.business.runner;

import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParser;
import com.asiainfo.dbx.ln.business.util.AppRunnerUtils;
import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.fail;

/**
 * Created by yanlei on 2014/9/20.
 */
@RunWith(JUnit4.class)
public class TestRunner {

    @BeforeClass
    public static void before() throws Exception {
        AppComponent.start();
        AppSpringUtils.getApplicationContextHolder().instanceApplicationContext("ftp","/config/spring/base/ftp_server_config.xml");
        System.out.println();
    }

    public RunnerXmlParser getRunnerXmlParser() throws Exception{
        return (RunnerXmlParser)AppSpringUtils.getApplicationContextHolder().getBean("runnerXmlParser");
    }
    @Test
    public void test() throws Throwable {
        try {
            ProcedureResourceDefine procedureResourceDefine = ResourceDefineFactory.getProcedureResourceDefine("lnbi","test","testFtp");
            AppRunnerUtils.getRunnerContainer(TestRunner.class).runProcedure(procedureResourceDefine, null, null);
        }catch(Throwable e){
           e.printStackTrace();
            fail();
        }
    }
    public static void main(String args []){
        try {
            TestRunner.before();
            new TestRunner().test();
        }catch(Throwable e){
            e.printStackTrace();;
        }
    }
}