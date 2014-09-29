package com.asiainfo.dbx.ln.component.util;

import com.asiainfo.dbx.ln.component.pool.ObjectRefPool;
import com.asiainfo.dbx.ln.component.var.VarConstant;
import com.asiainfo.dbx.ln.component.var.VarContainer;
import com.asiainfo.dbx.ln.component.var.VarContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by yanlei on 2014/7/10.
 */
public class AppVarUtils {

        private static final Logger logger = LoggerFactory.getLogger(AppVarUtils.class);

     /*   *//**
         * 获取变量值
         * @param name 变量名 不带前后缀（${,}）
         * @return
         * @throws Exception
         *//*
        public static Object getSignalVar(String name) throws Exception{
            return AppVarUtils.getVarContainer().getVar(VarConstant.VARPREFIX + name + VarConstant.VARSUBFIX);
        }
*/
    private volatile static VarContainer varContainer;

    public final static VarContainer getVarContainer(Class classz) throws Exception{
        if(AppVarUtils.varContainer == null){
            synchronized (AppVarUtils.class){
                if(AppVarUtils.varContainer ==null) {
                    VarContainer varContainer1 = (VarContainer) AppSpringUtils.getApplicationContextHolder().getBean("varContainer");
                    if (varContainer1 == null) {
                        throw new Exception("no bean named 'varContainer'");
                    }
                    AppVarUtils.varContainer = varContainer1;
                }
            }
        }
        return AppVarUtils.varContainer;
    }



        /**
         * 判断是否为变量
         * @param name
         * @return
         * @throws Exception
         */
        public final static boolean isVar(String name) throws Exception{
            return AppStringUtils.isVar(name,VarConstant.VARPREFIX,VarConstant.VARSUBFIX);
        }

    private final static  String convertVarString(String sql,String replaceString){
        int index = sql.indexOf(VarConstant.VARPREFIX);
        int endInex = sql.indexOf(VarConstant.VARSUBFIX);
        if(endInex == sql.length()-1){
            sql= sql.substring(0,index)+replaceString;
        }else{
            sql= sql.substring(0,index)+replaceString+sql.substring(endInex+1);
        }
        return sql;
    }

    public final static  String replaceVarString(String sql,String replaceString,int length){

        int index = sql.indexOf(VarConstant.VARPREFIX);
        int endInex = sql.indexOf(VarConstant.VARSUBFIX);
        if(endInex == sql.length()-1){
            sql= sql.substring(0,index)+replaceString;
        }else{
            sql= sql.substring(0,index)+replaceString+sql.substring(index+length);
        }
        return sql;
    }

    public final static String getVarOne(String strExpression) throws Exception{
      return AppStringUtils.getVarOne(strExpression,VarConstant.VARPREFIX,VarConstant.VARSUBFIX);
    }



    public final static List<String> getVarNames(String strExpression) throws Exception{
        return AppStringUtils.getVarNames(strExpression,VarConstant.VARPREFIX,VarConstant.VARSUBFIX);

    }


}
