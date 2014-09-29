package com.asiainfo.dbx.ln.business.runner;

import com.asiainfo.dbx.ln.component.api.Loader;
import com.asiainfo.dbx.ln.business.service.resource.ProcedureResourceDefine;

/**
 * Created by yanlei on 2014/9/22.
 */
public interface RunnerContainer extends Loader {
    public Object runProcedure(ProcedureResourceDefine procedureResourceDefine ,String paramVarName,String returnValueName) throws  Throwable;

}
