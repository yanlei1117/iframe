package com.asiainfo.dbx.ln.component.expression;

import com.asiainfo.dbx.ln.component.util.AppPoolUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yanlei on 2014/9/15.
 */
public class ExpressionEngineMvel  implements  ExpressionEngine{
    Map<String,CompiledExpression> compiledExpressionMap = new ConcurrentHashMap<String, CompiledExpression>();
    @Override
    public Object execute(String expression) throws Exception {



            CompiledExpression compiledExpression = compiledExpressionMap.get(expression);
            if (compiledExpression == null) {
                synchronized (expression) {
                    compiledExpression = compiledExpressionMap.get(expression);
                    if (compiledExpression == null) {
                        compiledExpression = new CompiledExpression();
                        compiledExpression.init(expression);
                        compiledExpressionMap.put(expression, compiledExpression);
                    }
                }
            }
            Object result = compiledExpression.execute(expression);
            return result;

    }

}
