package com.asiainfo.dbx.ln.component.expression;


import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.var.VarContainer;
import com.asiainfo.dbx.ln.component.var.impl.VarContainerOgnlImpl;
import ognl.Ognl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanlei on 2014/9/16.
 */
public class TestExpressionMvel {
    private Logger logger = LoggerFactory.getLogger(TestExpressionMvel.class);
    @Before
    public void before() throws Exception{
        AppComponent.start();
    }
    ExpressionEngine expressionEngineMvel = null;
    private ExpressionEngine getExpressionEngineMvel() throws Exception{
        if(expressionEngineMvel ==null){
            expressionEngineMvel = (ExpressionEngine)AppSpringUtils.getApplicationContextHolder().getBean("expressionEngineMvel");
        }
        return expressionEngineMvel;
    }

    ExpressionEngine expressionEngineOgnl = null;
    private ExpressionEngine getExpressionEngineOgnl() throws Exception{
        if(expressionEngineOgnl ==null) {
            expressionEngineOgnl = (ExpressionEngine) AppSpringUtils.getApplicationContextHolder().getBean("expressionEngine");
        }
        return expressionEngineOgnl;
    }


    VarContainer varContainer = null;
    private VarContainer getVarContainer(){
        if(varContainer ==null) {
            varContainer = (VarContainer) AppSpringUtils.getApplicationContextHolder().getBean("scopeDefaultVarContainer");
        }
        return varContainer;
    }
    @Test
    public void test(){
        try {
            Person person = new Person();
            person.setName("jack");
            String flag = "true";
            String num = "10";
            AppVarUtils.getVarContainer(this.getClass()).setVar("person", person);
            AppVarUtils.getVarContainer(this.getClass()).setVar("num", num);
            AppVarUtils.getVarContainer(this.getClass()).setVar("flag", flag);
            String expression="@{(num>5&&flag)?person.name+'10':person.age+1}";

            VarContainerOgnlImpl varContainerOgnl = new VarContainerOgnlImpl();
            varContainerOgnl.setVar("person", person);
            varContainerOgnl.setVar("num", num);
            varContainerOgnl.setVar("flag", flag);
            int times=1;
            for(int i=0;i<10;i++) {
                times+=i*1000;
                executeJava(times, person, flag, num);
                executeOgnl(times, expression);
                executeOgnlByDispatcher(times, expression);
                executeOgnlByVarContainerOgnl(times,expression,varContainerOgnl);
                executeOgnlOrigin(times, person, flag, num, expression);
                executeMvel(times, expression);
                executeMvelOrigin(times, person, flag, num, expression);
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public long executeOgnlByVarContainerOgnl(int times ,String expression,VarContainerOgnlImpl varContainerOgnl) throws Exception {
        long  startTime = System.currentTimeMillis();
        expression =  AppVarUtils.getVarOne(expression);
        for(int i=0;i<times;i++) {
            Object result = (Object) varContainerOgnl.getVar(expression);
            Assert.assertEquals("jack10", result.toString());
        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,executeOgnlByVarContainerOgnl use time:{}"+useTime);
        return useTime;
    }

    public long executeOgnlByDispatcher(int times ,String expression) throws Exception {
        long  startTime = System.currentTimeMillis();
        expression =  AppVarUtils.getVarOne(expression);
        for(int i=0;i<times;i++) {
            Object result = (Object) getVarContainer().getVar(expression);
            Assert.assertEquals("jack10", result.toString());
        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,ognlByDispatcher use time:{}"+useTime);
        return useTime;
    }

    public long executeOgnl(int times ,String expression) throws Exception {
        long  startTime = System.currentTimeMillis();
        for(int i=0;i<times;i++) {
            Object result = (Object) this.getExpressionEngineOgnl().execute(expression);
            Assert.assertEquals("jack10", result.toString());
        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,ognl use time:{}"+useTime);
        return useTime;
    }

    public long  executeMvel(int times ,String expression) throws Exception {
        long  startTime = System.currentTimeMillis();
        for(int i=0;i<times;i++) {
            Object result = (Object) getExpressionEngineMvel().execute(expression);
            Assert.assertEquals("jack10", result.toString());
        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,mvel use time:{}"+useTime);
        return useTime;
    }
    public long executeOgnlOrigin(int times ,Person person, String flag,String num,String newExpression) throws Exception{
        long  startTime = System.currentTimeMillis();
        Object tree = Ognl.parseExpression(AppVarUtils.getVarOne(newExpression));//表达式多次使用时，先编译比较好,供以后多次调用
        Map map  = new HashMap();
        map.put("num",num);
        map.put("flag",flag);
        map.put("person",person);

        for(int i=0;i<times;i++) {
            //@{(num>5&&flag)?person.name+'10':person.age+1}
            Object result = Ognl.getValue(tree, map);//取root的属性
            Assert.assertEquals("jack10", result.toString());

        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,ognl origin use time:{}"+useTime);
        return useTime;
    }

    public long executeMvelOrigin(int times ,Person person, String flag,String num,String newExpression) throws Exception{
        long  startTime = System.currentTimeMillis();
         newExpression = AppVarUtils.getVarOne(newExpression);
        Serializable compiledExpression = MVEL.compileExpression(AppVarUtils.getVarOne(newExpression));
        Map map  = new HashMap();
        map.put("num",Integer.parseInt(num));
        map.put("flag",Boolean.valueOf(flag));
        map.put("person",person);

        for(int i=0;i<times;i++) {
            //@{(num>5&&flag)?person.name+'10':person.age+1}
            Object result = MVEL.executeExpression(compiledExpression, map);
            Assert.assertEquals("jack10", result.toString());

        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,mvel origin use time:{}"+useTime);
        return useTime;
    }

    public long executeJava(int times ,Person person, String flag,String num){
        long  startTime = System.currentTimeMillis();
        for(int i=0;i<times;i++) {
            //@{(num>5&&flag)?person.name+'10':person.age+1}
            String result = null;
            if(Integer.parseInt(num)>5 && Boolean.valueOf(flag)){
                result =person.getName()+"10";
            }else{
                result =person.getAge()+1+"";
            }
            Assert.assertEquals("jack10", result.toString());

        }
        long useTime = System.currentTimeMillis()-startTime;
        System.out.println("run "+times+" times,java use time:{}"+useTime);
        return useTime;
    }

}
