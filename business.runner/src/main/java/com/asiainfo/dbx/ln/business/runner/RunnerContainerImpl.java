package com.asiainfo.dbx.ln.business.runner;

import com.asiainfo.dbx.ln.business.runner.parser.xml.RunnerXmlParser;
import com.asiainfo.dbx.ln.business.runner.runner.base.ProcedureRunner;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.component.util.AppResourceUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import ln.dbx.asiainfo.com.procedure.ProcedureDocument;
import ln.dbx.asiainfo.com.procedure.ProceduresDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/9/22.
 */
public class RunnerContainerImpl implements  RunnerContainer {
    private final static Logger logger = LoggerFactory.getLogger(RunnerContainerImpl.class);
    Map<ProcedureResourceDefine,ProcedureRunner> procedureRunnerMap = new ConcurrentHashMap<ProcedureResourceDefine,ProcedureRunner>();

    public Object runProcedure(ProcedureResourceDefine procedureResourceDefine ,String paramVarName,String returnValueName) throws  Throwable{
        ProcedureRunner procedureRunner = procedureRunnerMap.get(procedureResourceDefine);

        if(procedureRunner != null){
            if(AppValidationUtils.notEmpty(paramVarName) ) {
                String varName = procedureRunner.getParamsMapVarName();
                Object obj = AppVarUtils.getVarContainer(RunnerContainerImpl.class).getVar(paramVarName);
                AppVarUtils.getVarContainer(RunnerContainerImpl.class).setVar(varName, obj);
            }
            procedureRunner.run();
            String returnVarName = procedureRunner.getReturnVarName();
            if(returnVarName != null) {
                Object obj = AppVarUtils.getVarContainer(RunnerContainerImpl.class).getVar(returnVarName);
                if (AppValidationUtils.notEmpty(returnValueName)) {
                    AppVarUtils.getVarContainer(RunnerContainerImpl.class).setVar(returnValueName, obj);
                }
                return obj;
            }
        }else{
           logger.error("have no ProcedureRunner named '{}',return null",procedureResourceDefine);
        }
         return null;

    }
    RunnerXmlParser runnerXmlParser = null;

    public RunnerXmlParser getRunnerXmlParser() {
        return runnerXmlParser;
    }

    public void setRunnerXmlParser(RunnerXmlParser runnerXmlParser) {
        this.runnerXmlParser = runnerXmlParser;
    }

    public void load()  throws Exception{
        Resource[] resources = AppResourceUtils.getResources("classpath*:/runner/xml/*.xml");
        if(resources == null|| resources.length==0){
            logger.info("found runner xml:[]");
        }
        for(Resource resource :resources){
            logger.info("found runner xml:{}",resource.getURL().toString());
            ProceduresDocument proceduresDocument=  ProceduresDocument.Factory.parse(resource.getInputStream());
            ProceduresDocument.Procedures procedures  = proceduresDocument.getProcedures();
            ProcedureDocument.Procedure [] procedures1=  procedures.getProcedureArray();
            for(ProcedureDocument.Procedure procedure:procedures1){
                ProcedureRunner procedureRunner =  (ProcedureRunner)getRunnerXmlParser().parseXmlObject(procedure);
                if(procedureRunner == null){
                    throw new Exception(" generate  'null' runner from "+procedure.toString());
                }else{
                    ProcedureResourceDefine procedureResourceDefine =  ResourceDefineFactory.createProcedureResourceDefine(procedureRunner.getRepository(),procedureRunner.getCollection(),procedureRunner.getName());
                    logger.info("generate runner :{} ",procedureRunner);
                    procedureRunnerMap.put(procedureResourceDefine,procedureRunner);
                }
            }
        }
    }
}
