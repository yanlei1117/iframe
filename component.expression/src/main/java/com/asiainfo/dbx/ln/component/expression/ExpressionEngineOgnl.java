package com.asiainfo.dbx.ln.component.expression;

import com.asiainfo.dbx.ln.component.util.AppVarUtils;

/**
 * Created by yanlei on 2014/9/16.
 */
public class ExpressionEngineOgnl implements ExpressionEngine{
    @Override
    public Object execute(String expression) throws Exception {
        return AppVarUtils.getVarContainer(ExpressionEngineOgnl.class).getVar(expression);
    }
}
