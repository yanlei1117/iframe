package com.asiainfo.dbx.ln.component.var.impl.named;

import com.asiainfo.dbx.ln.component.util.AppDateUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.impl.factory.VarFactory;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by yanlei on 2014/9/15.
 */
public class SpecialTypeDateVarContainer extends DecorateVarContainer implements  SpecialTypeVarContainer{
    private Logger logger= LoggerFactory.getLogger(SpecialTypeDateVarContainer.class);
    @Override
    public boolean matchVarName(String varName) {
        return varName.toUpperCase().startsWith(VarConstant.VAR_PREFIX_DATE);
    }
    @Override
    public String getVarNamePrefix() {
        return  VarConstant.VAR_PREFIX_DATE;
    }
    @Override
    public Object getVar(String name) throws Exception {
        String dateExpression = name.substring(VarConstant.VAR_PREFIX_DATE.length());
        VarFactory varFactory = new DateVarFactory(dateExpression);
        Object  obj = varFactory.getVarValue();
        return obj;
    }

    @Override
    public void setVar(String name, Object obj) throws Exception {
        String expression = (String)obj;
        final String newExpression =expression.substring(VarConstant.VAR_PREFIX_DATE.length());
        VarFactory varFactory = new DateVarFactory(newExpression);
        this.setVar(name,varFactory);

    }

    @Override
    public boolean containsVar(String name) {
        return false;
    }

    @Override
    public void clear() {

    }

    class DateVarFactory implements  VarFactory{
            String expression = null;
            public DateVarFactory(String expression){
                this.expression = expression;
            }
            public Object getVarValue() throws Exception {

                List<String> list = AppVarUtils.getVarNames(expression);
                if (list == null || list.size() == 0) {
                    throw new Exception("date not have var");
                }
                String var = list.get(0);
                String value = expression.substring(var.length());
                Object obj = AppVarUtils.getVarContainer(this.getClass()).getVar(var);
                if (obj != null) {
                    Date date = null;
                    if (obj instanceof Date) {
                        date = (Date) obj;
                    } else {
                        String time = obj.toString().trim();

                        date = (Date) ConvertUtils.convert(time, Date.class);

                        if (date == null) {
                            throw new Exception(" expression:" + expression + " date var:" + var + " not a date type");
                        }
                    }

                    expression = (String) AppVarUtils.getVarContainer(this.getClass()).getVar(value);
                    Object newObj = AppDateUtils.parseDateByExpression(date, expression);
                    logger.debug(" DateOperatorVarFactory expression:" + expression + " result:" + newObj + " finish");
                    return newObj;

                } else {
                    return null;
                }

            }
    }
}
