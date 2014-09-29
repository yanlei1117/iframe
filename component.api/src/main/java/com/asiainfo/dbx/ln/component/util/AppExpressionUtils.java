package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.expression.ExpressionEngine;
import com.asiainfo.dbx.ln.component.pool.ObjectRefPool;

/**
 * Created by yanlei on 2014/9/15.
 */
public class AppExpressionUtils {
    private volatile  static ExpressionEngine expressionEngine;
    public static ExpressionEngine getExpressionEngine(Class classz) throws Exception{
        if(expressionEngine == null){
            synchronized (AppExpressionUtils.class){
                if(expressionEngine == null) {
                    ExpressionEngine expressionEngine1 = (ExpressionEngine) AppSpringUtils.getApplicationContextHolder().getBean("expressionEngine");
                    if (expressionEngine1 == null) {
                        throw new Exception("no bean named 'expressionEngine'");
                    }
                    expressionEngine = expressionEngine1;
                }

            }
        }
        return expressionEngine;
    }
}
