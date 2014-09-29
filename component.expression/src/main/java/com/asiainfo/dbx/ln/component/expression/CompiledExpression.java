package com.asiainfo.dbx.ln.component.expression;

import com.asiainfo.dbx.ln.component.util.AppPoolUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/15.
 */
public class CompiledExpression implements ExpressionEngine{
    private Logger logger = LoggerFactory.getLogger(CompiledExpression.class);
    String srcExpression;
    String newExpression;
    Serializable compiledExpression;
    List<String> varlist = null;

    public void init(String srcExpression) throws Exception{
        this.srcExpression = srcExpression;
        this.newExpression = srcExpression;
        this.varlist = AppVarUtils.getVarNames(srcExpression);
        if(varlist != null && varlist.size()>0) {
            for (int i=0;i<varlist.size();i++) {
                String var = varlist.get(i);
                newExpression = AppVarUtils.replaceVarString(newExpression, "var"+i, var.length());
            }
        }
        compiledExpression = MVEL.compileExpression(newExpression);
    }
    @Override
    public Object execute(String expression) throws Exception {
        logger.debug("execute expression:{}",expression);
        Map map = (Map) AppPoolUtils.getObjectRefPool(CompiledExpression.class).borrowObject(HashMap.class.getName());
        try {

            if (this.varlist != null && varlist.size() > 0) {
                for (int i = 0; i < varlist.size(); i++) {
                    String var = varlist.get(i);
                    Object obj = AppVarUtils.getVarContainer(this.getClass()).getVar(var);
                    logger.debug("execute expression: the value of var named '{}' is {}", var, obj);
                    map.put("var" + i, obj);
                }

            }
            Object obj = MVEL.executeExpression(compiledExpression, map);
            return obj;
        }finally{
            AppPoolUtils.getObjectRefPool(CompiledExpression.class).returnObject(map,HashMap.class.getName());
        }
    }
}
