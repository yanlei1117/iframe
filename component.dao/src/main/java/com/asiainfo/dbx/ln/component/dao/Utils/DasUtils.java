package com.asiainfo.dbx.ln.component.dao.Utils;

import com.asiainfo.dbx.ln.component.dao.RowLimit;

import java.sql.Connection;

/**
 * Created by Administrator on 2014/8/4.
 */
public class DasUtils {
    public static  String paginationSql(Connection connection,String oldSql, RowLimit rowLimit) throws Exception{
        String productName = connection.getMetaData().getDatabaseProductName();
        if(productName.toLowerCase().indexOf("oracle")!= -1){
            String start = " SELECT * FROM   (SELECT   row_.*, ROWNUM rownum_ FROM ( ";
            String end = " ) TEMP_ WHERE   ROWNUM <= " + rowLimit.getLimit()
                    + ") WHERE   rownum_ > " + rowLimit.getOffset();
            return start + oldSql + end;
        }else if(productName.toLowerCase().indexOf("mysql")!= -1){
            return "SELECT * FROM ("+oldSql+") TEMP_  limit "+rowLimit.getOffset()+","+rowLimit.getLimit();
        }else{
            throw new Exception(" not support database product name:{}"+productName);
        }

    }
}
